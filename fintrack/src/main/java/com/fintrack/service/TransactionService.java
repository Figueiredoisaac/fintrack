package com.fintrack.service;

import com.fintrack.dto.TransactionDTO;
import com.fintrack.entity.BankAccount;
import com.fintrack.entity.Category;
import com.fintrack.entity.Transaction;
import com.fintrack.entity.User;
import com.fintrack.repository.BankAccountRepository;
import com.fintrack.repository.CategoryRepository;
import com.fintrack.repository.TransactionRepository;
import com.fintrack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    // CRUD Operations
    public List<TransactionDTO> findAll() {
        return transactionRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<TransactionDTO> findById(Long id) {
        return transactionRepository.findById(id)
                .map(this::convertToDTO);
    }

    public List<TransactionDTO> findByUser(User user) {
        return transactionRepository.findByUserOrderByTransactionDateDesc(user, Pageable.unpaged()).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Page<TransactionDTO> findByUser(User user, Pageable pageable) {
        return transactionRepository.findByUserOrderByTransactionDateDesc(user, pageable)
                .map(this::convertToDTO);
    }

    public List<TransactionDTO> findByUserAndCategory(User user, Long categoryId) {
        return transactionRepository.findByUserAndCategory(user, categoryId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<TransactionDTO> findByUserAndBankAccount(User user, Long accountId) {
        return transactionRepository.findByUserAndAccount(user, accountId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<TransactionDTO> findByUserAndDateRange(User user, LocalDate startDate, LocalDate endDate) {
        return transactionRepository.findByUserAndDateRange(user, startDate, endDate).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public BigDecimal getTotalByUserAndTypeAndDateRange(User user, Transaction.TransactionType type, LocalDate startDate, LocalDate endDate) {
        return transactionRepository.getTotalByUserAndTypeAndDateRange(user, type, startDate, endDate);
    }

    public Long countByUserAndDateRange(User user, LocalDate startDate, LocalDate endDate) {
        return Optional.ofNullable(user)
                .map(u -> transactionRepository.countByUserAndDateRange(u, startDate, endDate))
                .orElse(0L);
    }

    @Transactional
    public TransactionDTO create(TransactionDTO transactionDTO) {
        User user = userRepository.findById(transactionDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Category category = categoryRepository.findById(transactionDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        
        BankAccount bankAccount = bankAccountRepository.findById(transactionDTO.getBankAccountId())
                .orElseThrow(() -> new RuntimeException("Bank account not found"));
        
        Transaction transaction = convertToEntity(transactionDTO);
        transaction.setUser(user);
        transaction.setCategory(category);
        transaction.setBankAccount(bankAccount);
        
        Transaction savedTransaction = transactionRepository.save(transaction);
        
        // Update bank account balance
        updateBankAccountBalance(bankAccount, transaction);
        
        return convertToDTO(savedTransaction);
    }

    @Transactional
    public TransactionDTO update(Long id, TransactionDTO transactionDTO) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        // Store old values for balance adjustment
        BigDecimal oldAmount = transaction.getAmount();
        Transaction.TransactionType oldType = transaction.getType();
        BankAccount oldBankAccount = transaction.getBankAccount();

        // Update transaction data
        transaction.setDescription(transactionDTO.getDescription());
        transaction.setObservations(transactionDTO.getObservations());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setTransactionDate(transactionDTO.getTransactionDate());
        transaction.setType(transactionDTO.getType());
        transaction.setStatus(transactionDTO.getStatus());

        // Update category if necessary
        if (!transaction.getCategory().getId().equals(transactionDTO.getCategoryId())) {
            Category newCategory = categoryRepository.findById(transactionDTO.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            transaction.setCategory(newCategory);
        }

        // Update bank account if necessary
        if (!transaction.getBankAccount().getId().equals(transactionDTO.getBankAccountId())) {
            BankAccount newBankAccount = bankAccountRepository.findById(transactionDTO.getBankAccountId())
                    .orElseThrow(() -> new RuntimeException("Bank account not found"));
            
            // Revert balance in old account
            revertBankAccountBalance(oldBankAccount, oldAmount, oldType);
            
            // Update to new account
            transaction.setBankAccount(newBankAccount);
            updateBankAccountBalance(newBankAccount, transaction);
        } else {
            // Same account, adjust balance
            adjustBankAccountBalance(oldBankAccount, oldAmount, oldType, transaction);
        }

        Transaction savedTransaction = transactionRepository.save(transaction);
        return convertToDTO(savedTransaction);
    }

    @Transactional
    public void delete(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        // Revert bank account balance
        revertBankAccountBalance(transaction.getBankAccount(), transaction.getAmount(), transaction.getType());

        transactionRepository.delete(transaction);
    }

    // Balance management methods
    private void updateBankAccountBalance(BankAccount bankAccount, Transaction transaction) {
        if (transaction.getStatus() == Transaction.TransactionStatus.CONFIRMED) {
            switch (transaction.getType()) {
                case INCOME:
                    bankAccount.addBalance(transaction.getAmount());
                    break;
                case EXPENSE:
                    bankAccount.subtractBalance(transaction.getAmount());
                    break;
                case TRANSFER:
                    // Transfer logic would be implemented here
                    break;
            }
            bankAccountRepository.save(bankAccount);
        }
    }

    private void revertBankAccountBalance(BankAccount bankAccount, BigDecimal amount, Transaction.TransactionType type) {
        switch (type) {
            case INCOME:
                bankAccount.subtractBalance(amount);
                break;
            case EXPENSE:
                bankAccount.addBalance(amount);
                break;
            case TRANSFER:
                // Transfer revert logic would be implemented here
                break;
        }
        bankAccountRepository.save(bankAccount);
    }

    private void adjustBankAccountBalance(BankAccount bankAccount, BigDecimal oldAmount, 
                                        Transaction.TransactionType oldType, Transaction newTransaction) {
        // Revert old transaction
        revertBankAccountBalance(bankAccount, oldAmount, oldType);
        // Apply new transaction
        updateBankAccountBalance(bankAccount, newTransaction);
    }

    // Conversion methods
    private TransactionDTO convertToDTO(Transaction transaction) {
        TransactionDTO dto = new TransactionDTO();
        dto.setId(transaction.getId());
        dto.setDescription(transaction.getDescription());
        dto.setObservations(transaction.getObservations());
        dto.setAmount(transaction.getAmount());
        dto.setTransactionDate(transaction.getTransactionDate());
        dto.setType(transaction.getType());
        dto.setStatus(transaction.getStatus());
        
        if (transaction.getCategory() != null) {
            dto.setCategoryId(transaction.getCategory().getId());
            dto.setCategoryName(transaction.getCategory().getName());
        }
        
        if (transaction.getBankAccount() != null) {
            dto.setBankAccountId(transaction.getBankAccount().getId());
            dto.setBankAccountName(transaction.getBankAccount().getName());
        }
        
        if (transaction.getUser() != null) {
            dto.setUserId(transaction.getUser().getId());
        }
        
        dto.setCreatedAt(transaction.getCreatedAt());
        dto.setUpdatedAt(transaction.getUpdatedAt());
        
        return dto;
    }

    private Transaction convertToEntity(TransactionDTO dto) {
        Transaction transaction = new Transaction();
        transaction.setDescription(dto.getDescription());
        transaction.setObservations(dto.getObservations());
        transaction.setAmount(dto.getAmount());
        transaction.setTransactionDate(dto.getTransactionDate());
        transaction.setType(dto.getType());
        transaction.setStatus(dto.getStatus() != null ? dto.getStatus() : Transaction.TransactionStatus.CONFIRMED);
        
        return transaction;
    }
} 