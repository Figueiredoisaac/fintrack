package com.fintrack.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RecentTransactionDTO {
    
    private Long id;
    private String description;
    private BigDecimal amount;
    private LocalDate transactionDate;
    private String type;
    private String status;
    private String categoryName;
    private String accountName;
    private String categoryColor;
    private String categoryIcon;
    
    // Construtores
    public RecentTransactionDTO() {}
    
    public RecentTransactionDTO(Long id, String description, BigDecimal amount, LocalDate transactionDate,
                              String type, String status, String categoryName, String accountName,
                              String categoryColor, String categoryIcon) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.type = type;
        this.status = status;
        this.categoryName = categoryName;
        this.accountName = accountName;
        this.categoryColor = categoryColor;
        this.categoryIcon = categoryIcon;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public LocalDate getTransactionDate() {
        return transactionDate;
    }
    
    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getCategoryName() {
        return categoryName;
    }
    
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    public String getAccountName() {
        return accountName;
    }
    
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
    
    public String getCategoryColor() {
        return categoryColor;
    }
    
    public void setCategoryColor(String categoryColor) {
        this.categoryColor = categoryColor;
    }
    
    public String getCategoryIcon() {
        return categoryIcon;
    }
    
    public void setCategoryIcon(String categoryIcon) {
        this.categoryIcon = categoryIcon;
    }
} 