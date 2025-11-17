package com.fintrack.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class DashboardDTO {
    
    private BigDecimal totalBalance;
    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    private BigDecimal monthlyBalance;
    private BigDecimal monthlyIncome;
    private BigDecimal monthlyExpense;
    private List<AccountBalanceDTO> accountBalances;
    private List<CategorySummaryDTO> topCategories;
    private Map<String, BigDecimal> monthlyChart;
    private List<RecentTransactionDTO> recentTransactions;
    private LocalDate periodStart;
    private LocalDate periodEnd;
    
    // Construtores
    public DashboardDTO() {}
    
    public DashboardDTO(BigDecimal totalBalance, BigDecimal totalIncome, BigDecimal totalExpense,
                       BigDecimal monthlyBalance, BigDecimal monthlyIncome, BigDecimal monthlyExpense) {
        this.totalBalance = totalBalance;
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.monthlyBalance = monthlyBalance;
        this.monthlyIncome = monthlyIncome;
        this.monthlyExpense = monthlyExpense;
    }
    
    // Getters e Setters
    public BigDecimal getTotalBalance() {
        return totalBalance;
    }
    
    public void setTotalBalance(BigDecimal totalBalance) {
        this.totalBalance = totalBalance;
    }
    
    public BigDecimal getTotalIncome() {
        return totalIncome;
    }
    
    public void setTotalIncome(BigDecimal totalIncome) {
        this.totalIncome = totalIncome;
    }
    
    public BigDecimal getTotalExpense() {
        return totalExpense;
    }
    
    public void setTotalExpense(BigDecimal totalExpense) {
        this.totalExpense = totalExpense;
    }
    
    public BigDecimal getMonthlyBalance() {
        return monthlyBalance;
    }
    
    public void setMonthlyBalance(BigDecimal monthlyBalance) {
        this.monthlyBalance = monthlyBalance;
    }
    
    public BigDecimal getMonthlyIncome() {
        return monthlyIncome;
    }
    
    public void setMonthlyIncome(BigDecimal monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }
    
    public BigDecimal getMonthlyExpense() {
        return monthlyExpense;
    }
    
    public void setMonthlyExpense(BigDecimal monthlyExpense) {
        this.monthlyExpense = monthlyExpense;
    }
    
    public List<AccountBalanceDTO> getAccountBalances() {
        return accountBalances;
    }
    
    public void setAccountBalances(List<AccountBalanceDTO> accountBalances) {
        this.accountBalances = accountBalances;
    }
    
    public List<CategorySummaryDTO> getTopCategories() {
        return topCategories;
    }
    
    public void setTopCategories(List<CategorySummaryDTO> topCategories) {
        this.topCategories = topCategories;
    }
    
    public Map<String, BigDecimal> getMonthlyChart() {
        return monthlyChart;
    }
    
    public void setMonthlyChart(Map<String, BigDecimal> monthlyChart) {
        this.monthlyChart = monthlyChart;
    }
    
    public List<RecentTransactionDTO> getRecentTransactions() {
        return recentTransactions;
    }
    
    public void setRecentTransactions(List<RecentTransactionDTO> recentTransactions) {
        this.recentTransactions = recentTransactions;
    }
    
    public LocalDate getPeriodStart() {
        return periodStart;
    }
    
    public void setPeriodStart(LocalDate periodStart) {
        this.periodStart = periodStart;
    }
    
    public LocalDate getPeriodEnd() {
        return periodEnd;
    }
    
    public void setPeriodEnd(LocalDate periodEnd) {
        this.periodEnd = periodEnd;
    }
} 