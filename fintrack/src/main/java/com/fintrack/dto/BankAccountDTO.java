package com.fintrack.dto;

import com.fintrack.entity.BankAccount;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BankAccountDTO {
    
    private Long id;
    
    @NotBlank(message = "Account name is required")
    @Size(min = 2, max = 100, message = "Account name must be between 2 and 100 characters")
    private String name;
    
    @Size(max = 200, message = "Description must have at most 200 characters")
    private String description;
    
    @NotBlank(message = "Bank institution is required")
    @Size(min = 2, max = 100, message = "Institution must be between 2 and 100 characters")
    private String institution;
    
    private String accountNumber;
    
    private String agency;
    
    @NotNull(message = "Account type is required")
    private BankAccount.AccountType type;
    
    @NotNull(message = "Initial balance is required")
    private BigDecimal initialBalance;
    
    private BigDecimal currentBalance;
    
    private String color;
    
    private String icon;
    
    @NotNull(message = "User is required")
    private Long userId;
    
    private Boolean active;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;

    // Constructors
    public BankAccountDTO() {}

    public BankAccountDTO(String name, String institution, BankAccount.AccountType type, 
                         BigDecimal initialBalance, Long userId) {
        this.name = name;
        this.institution = institution;
        this.type = type;
        this.initialBalance = initialBalance;
        this.currentBalance = initialBalance;
        this.userId = userId;
        this.active = true;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public BankAccount.AccountType getType() {
        return type;
    }

    public void setType(BankAccount.AccountType type) {
        this.type = type;
    }

    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "BankAccountDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", institution='" + institution + '\'' +
                ", type=" + type +
                ", currentBalance=" + currentBalance +
                ", active=" + active +
                '}';
    }
} 