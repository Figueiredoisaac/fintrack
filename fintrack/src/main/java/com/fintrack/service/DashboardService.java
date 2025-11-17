package com.fintrack.service;

import com.fintrack.dto.*;
import com.fintrack.entity.BankAccount;
import com.fintrack.entity.Category;
import com.fintrack.entity.Transaction;
import com.fintrack.repository.BankAccountRepository;
import com.fintrack.repository.CategoryRepository;
import com.fintrack.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DashboardService {
    
    @Autowired
    private TransactionRepository transactionRepository;
    
    @Autowired
    private BankAccountRepository bankAccountRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    public DashboardDTO getDashboard(Long userId, LocalDate startDate, LocalDate endDate) {
        DashboardDTO dashboard = new DashboardDTO();
        
        // Period
        LocalDate startDate = (dashboard.startDate != null) ? dashboard.startDate : LocalDate.now().withDayOfMonth(1);
        LocalDate endDate = (dashboard.endDate != null) ? dashboard.endDate : LocalDate.now();

        // Total balances
        List<BankAccount> accounts = bankAccountRepository.findByUserId(userId);
        BigDecimal totalBalance = accounts.stream()
                .map(BankAccount::getCurrentBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalIncome = transactionRepository.getTotalByUserAndTypeAndDateRange(
                userId, Transaction.TransactionType.INCOME, startDate, endDate);
        BigDecimal totalExpense = transactionRepository.getTotalByUserAndTypeAndDateRange(
                userId, Transaction.TransactionType.EXPENSE, startDate, endDate);

        // Current month totals
        LocalDate monthStart = LocalDate.now().withDayOfMonth(1);
        LocalDate monthEnd = LocalDate.now();

        BigDecimal monthlyIncome = transactionRepository.getTotalByUserAndTypeAndDateRange(
                userId, Transaction.TransactionType.INCOME, monthStart, monthEnd);
        BigDecimal monthlyExpense = transactionRepository.getTotalByUserAndTypeAndDateRange(
                userId, Transaction.TransactionType.EXPENSE, monthStart, monthEnd);
        BigDecimal monthlyBalance = monthlyIncome.subtract(monthlyExpense);

        // Get aggregated data
        List<AccountBalanceDTO> accountBalances = getAccountBalances(userId);
        List<CategorySummaryDTO> topCategories = getTopCategories(userId, startDate, endDate);

        // Monthly chart
        Map<String, BigDecimal> monthlyChart = getMonthlyChart(userId, startDate, endDate);

        // Recent transactions
        List<RecentTransactionDTO> recentTransactions = getRecentTransactions(userId, 10);
        
        return dashboard;
    }
    
    private List<AccountBalanceDTO> getAccountBalances(Long userId) {
        List<BankAccount> accounts = bankAccountRepository.findByUserId(userId);
        
        return accounts.stream()
                .map(account -> new AccountBalanceDTO(
                        account.getId(),
                        account.getName(),
                        account.getInstitution(),
                        account.getType().toString(),
                        account.getCurrentBalance(),
                        account.getColor(),
                        account.getIcon()
                ))
                .collect(Collectors.toList());
    }
    
    private List<CategorySummaryDTO> getTopCategories(Long userId, LocalDate startDate, LocalDate endDate) {
        List<Object[]> categoryStats = transactionRepository.getCategoryStatsByUserAndDateRange(
                userId, startDate, endDate);
        
        List<CategorySummaryDTO> categories = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;
        
        // Primeiro, calcular o total para calcular porcentagens
        for (Object[] stat : categoryStats) {
            BigDecimal amount = (BigDecimal) stat[2];
            if (amount != null) {
                totalAmount = totalAmount.add(amount.abs());
            }
        }
        
        // Agora criar os DTOs com porcentagens
        for (Object[] stat : categoryStats) {
            Long categoryId = (Long) stat[0];
            String categoryName = (String) stat[1];
            BigDecimal amount = (BigDecimal) stat[2];
            Long count = (Long) stat[3];
            String categoryType = (String) stat[4];
            
            if (amount != null && count != null) {
                Category category = categoryRepository.findById(categoryId).orElse(null);
                if (category != null) {
                    BigDecimal percentage = totalAmount.compareTo(BigDecimal.ZERO) > 0 ?
                            amount.abs().divide(totalAmount, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)) :
                            BigDecimal.ZERO;
                    
                    CategorySummaryDTO dto = new CategorySummaryDTO(
                            categoryId,
                            categoryName,
                            categoryType,
                            amount.abs(),
                            count,
                            category.getColor(),
                            category.getIcon()
                    );
                    dto.setPercentage(percentage);
                    categories.add(dto);
                }
            }
        }
        
        // Ordenar por valor total (maior primeiro) e limitar a 10
        return categories.stream()
                .sorted((c1, c2) -> c2.getTotalAmount().compareTo(c1.getTotalAmount()))
                .limit(10)
                .collect(Collectors.toList());
    }
    
    private Map<String, BigDecimal> getMonthlyChart(Long userId, LocalDate startDate, LocalDate endDate) {
        Map<String, BigDecimal> monthlyChart = new LinkedHashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM/yyyy");
        
        LocalDate current = startDate.withDayOfMonth(1);
        while (!current.isAfter(endDate)) {
            LocalDate monthStart = current;
            LocalDate monthEnd = current.withDayOfMonth(current.lengthOfMonth());
            
            BigDecimal monthIncome = transactionRepository.getTotalByUserAndTypeAndDateRange(
                    userId, Transaction.TransactionType.INCOME, monthStart, monthEnd);
            BigDecimal monthExpense = transactionRepository.getTotalByUserAndTypeAndDateRange(
                    userId, Transaction.TransactionType.EXPENSE, monthStart, monthEnd);
            
            BigDecimal monthBalance = BigDecimal.ZERO;
            if (monthIncome != null) monthBalance = monthBalance.add(monthIncome);
            if (monthExpense != null) monthBalance = monthBalance.add(monthExpense);
            
            monthlyChart.put(current.format(formatter), monthBalance);
            current = current.plusMonths(1);
        }
        
        return monthlyChart;
    }
    
    private List<RecentTransactionDTO> getRecentTransactions(Long userId, int limit) {
        List<Transaction> transactions = transactionRepository.findRecentByUser(userId, limit);
        
        return transactions.stream()
                .map(transaction -> {
                    Category category = transaction.getCategory();
                    BankAccount account = transaction.getBankAccount();
                    
                    return new RecentTransactionDTO(
                            transaction.getId(),
                            transaction.getDescription(),
                            transaction.getAmount(),
                            transaction.getTransactionDate(),
                            transaction.getType().toString(),
                            transaction.getStatus().toString(),
                            category != null ? category.getName() : "Sem categoria",
                            account != null ? account.getName() : "Sem conta",
                            category != null ? category.getColor() : "#6c757d",
                            category != null ? category.getIcon() : "??"
                    );
                })
                .collect(Collectors.toList());
    }
} 