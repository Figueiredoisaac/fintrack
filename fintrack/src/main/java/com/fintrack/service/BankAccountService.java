package com.fintrack.service;

import com.fintrack.dto.BankAccountDTO;
import com.fintrack.entity.BankAccount;
import com.fintrack.entity.User;
import com.fintrack.repository.BankAccountRepository;
import com.fintrack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private UserRepository userRepository;

    // CRUD Operations
    public List<BankAccountDTO> findAll() {
        return bankAccountRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<BankAccountDTO> findById(Long id) {
        return bankAccountRepository.findById(id)
                .map(this::convertToDTO);
    }

    public List<BankAccountDTO> findByUser(User user) {
        return bankAccountRepository.findByUser(user).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<BankAccountDTO> findByUserAndActive(User user, Boolean active) {
        return bankAccountRepository.findByUserAndActive(user, active).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public BigDecimal getTotalBalanceByUser(User user) {
        return bankAccountRepository.getTotalBalanceByUser(user);
    }

    public boolean existsByNameAndUser(String name, User user) {
        return bankAccountRepository.existsByNameAndUserAndActive(name, user, true);
    }

    public BankAccountDTO create(BankAccountDTO bankAccountDTO) {
        User user = userRepository.findById(bankAccountDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        BankAccount bankAccount = convertToEntity(bankAccountDTO);
        bankAccount.setUser(user);

        BankAccount savedBankAccount = bankAccountRepository.save(bankAccount);
        return convertToDTO(savedBankAccount);
    }

    public Optional<BankAccountDTO> update(Long id, BankAccountDTO bankAccountDTO) {
        return bankAccountRepository.findById(id)
                .map(existingBankAccount -> {
                    existingBankAccount.setName(bankAccountDTO.getName());
                    existingBankAccount.setDescription(bankAccountDTO.getDescription());
                    existingBankAccount.setInstitution(bankAccountDTO.getInstitution());
                    existingBankAccount.setAccountNumber(bankAccountDTO.getAccountNumber());
                    existingBankAccount.setAgency(bankAccountDTO.getAgency());
                    existingBankAccount.setType(bankAccountDTO.getType());
                    existingBankAccount.setInitialBalance(bankAccountDTO.getInitialBalance());
                    existingBankAccount.setCurrentBalance(bankAccountDTO.getCurrentBalance());
                    existingBankAccount.setColor(bankAccountDTO.getColor());
                    existingBankAccount.setIcon(bankAccountDTO.getIcon());
                    existingBankAccount.setActive(bankAccountDTO.getActive());

                    BankAccount savedBankAccount = bankAccountRepository.save(existingBankAccount);
                    return convertToDTO(savedBankAccount);
                });
    }

    public boolean delete(Long id) {
        return bankAccountRepository.findById(id)
                .map(bankAccount -> {
                    bankAccount.setActive(false);
                    bankAccountRepository.save(bankAccount);
                    return true;
                })
                .orElse(false);
    }

    public void updateBalance(Long accountId, BigDecimal amount) {
        bankAccountRepository.findById(accountId)
                .ifPresent(account -> {
                    account.setCurrentBalance(account.getCurrentBalance().add(amount));
                    bankAccountRepository.save(account);
                });
    }

    // Conversion methods
    private BankAccountDTO convertToDTO(BankAccount bankAccount) {
        BankAccountDTO dto = new BankAccountDTO();
        dto.setId(bankAccount.getId());
        dto.setName(bankAccount.getName());
        dto.setDescription(bankAccount.getDescription());
        dto.setInstitution(bankAccount.getInstitution());
        dto.setAccountNumber(bankAccount.getAccountNumber());
        dto.setAgency(bankAccount.getAgency());
        dto.setType(bankAccount.getType());
        dto.setInitialBalance(bankAccount.getInitialBalance());
        dto.setCurrentBalance(bankAccount.getCurrentBalance());
        dto.setColor(bankAccount.getColor());
        dto.setIcon(bankAccount.getIcon());
        dto.setUserId(bankAccount.getUser().getId());
        dto.setActive(bankAccount.getActive());
        dto.setCreatedAt(bankAccount.getCreatedAt());
        dto.setUpdatedAt(bankAccount.getUpdatedAt());
        return dto;
    }

    private BankAccount convertToEntity(BankAccountDTO dto) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setName(dto.getName());
        bankAccount.setDescription(dto.getDescription());
        bankAccount.setInstitution(dto.getInstitution());
        bankAccount.setAccountNumber(dto.getAccountNumber());
        bankAccount.setAgency(dto.getAgency());
        bankAccount.setType(dto.getType());
        bankAccount.setInitialBalance(dto.getInitialBalance());
        bankAccount.setCurrentBalance(dto.getCurrentBalance());
        bankAccount.setColor(dto.getColor());
        bankAccount.setIcon(dto.getIcon());
        bankAccount.setActive(dto.getActive() != null ? dto.getActive() : true);
        return bankAccount;
    }
} 