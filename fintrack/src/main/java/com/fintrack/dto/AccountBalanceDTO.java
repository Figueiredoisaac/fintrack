package com.fintrack.dto;

import java.math.BigDecimal;

public class AccountBalanceDTO {
    
    private Long accountId;
    private String accountName;
    private String institution;
    private String accountType;
    private BigDecimal balance;
    private String color;
    private String icon;
    
    // Construtores
    public AccountBalanceDTO() {}
    
    public AccountBalanceDTO(Long accountId, String accountName, String institution, 
                           String accountType, BigDecimal balance, String color, String icon) {
        this.accountId = accountId;
        this.accountName = accountName;
        this.institution = institution;
        this.accountType = accountType;
        this.balance = balance;
        this.color = color;
        this.icon = icon;
    }
    
    // Getters e Setters
    public Long getAccountId() {
        return accountId;
    }
    
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
    
    public String getAccountName() {
        return accountName;
    }
    
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
    
    public String getInstitution() {
        return institution;
    }
    
    public void setInstitution(String institution) {
        this.institution = institution;
    }
    
    public String getAccountType() {
        return accountType;
    }
    
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
    
    public BigDecimal getBalance() {
        return balance;
    }
    
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    
    public String getColor() {
        return color;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
    
    public String getIcon() {
        return icon;
    }
    
    public void setIcon(String icon) {
        this.icon = icon;
    }
} 