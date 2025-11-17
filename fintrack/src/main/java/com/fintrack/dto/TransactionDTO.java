package com.fintrack.dto;

import com.fintrack.entity.Transaction;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TransactionDTO {
    
    private Long id;
    
    @NotBlank(message = "Description is required")
    @Size(min = 2, max = 200, message = "Description must be between 2 and 200 characters")
    private String description;
    
    @Size(max = 500, message = "Observations must have at most 500 characters")
    private String observations;
    
    @NotNull(message = "Amount is required")
    private BigDecimal amount;
    
    @NotNull(message = "Date is required")
    private LocalDate transactionDate;
    
    @NotNull(message = "Transaction type is required")
    private Transaction.TransactionType type;
    
    private Transaction.TransactionStatus status;
    
    @NotNull(message = "Category is required")
    private Long categoryId;
    
    @NotNull(message = "Bank account is required")
    private Long bankAccountId;
    
    private Long userId;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;

    // Additional fields for display
    private String categoryName;
    private String bankAccountName;

    // Constructors
    public TransactionDTO() {}

    public TransactionDTO(String description, BigDecimal amount, LocalDate transactionDate,
                         Transaction.TransactionType type, Long categoryId, Long bankAccountId, Long userId) {
        this.description = description;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.type = type;
        this.categoryId = categoryId;
        this.bankAccountId = bankAccountId;
        this.userId = userId;
        this.status = Transaction.TransactionStatus.CONFIRMED;
    }

    // Getters and Setters
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

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
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

    public Transaction.TransactionType getType() {
        return type;
    }

    public void setType(Transaction.TransactionType type) {
        this.type = type;
    }

    public Transaction.TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(Transaction.TransactionStatus status) {
        this.status = status;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(Long bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    @Override
    public String toString() {
        return "TransactionDTO{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", transactionDate=" + transactionDate +
                ", type=" + type +
                ", status=" + status +
                '}';
    }
} 