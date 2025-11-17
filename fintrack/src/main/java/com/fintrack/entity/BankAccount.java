package com.fintrack.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "bank_accounts")
public class BankAccount {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Account name is required")
    @Size(min = 2, max = 100, message = "Account name must be between 2 and 100 characters")
    @Column(name = "name", nullable = false)
    private String name;
    
    @Size(max = 200, message = "Description must have at most 200 characters")
    @Column(name = "description")
    private String description;
    
    @NotBlank(message = "Bank institution is required")
    @Size(min = 2, max = 100, message = "Institution must be between 2 and 100 characters")
    @Column(name = "institution", nullable = false)
    private String institution;
    
    @Column(name = "account_number")
    private String accountNumber;
    
    @Column(name = "agency")
    private String agency;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private AccountType type;
    
    @NotNull(message = "Initial balance is required")
    @Column(name = "initial_balance", nullable = false, precision = 15, scale = 2)
    private BigDecimal initialBalance;
    
    @Column(name = "current_balance", nullable = false, precision = 15, scale = 2)
    private BigDecimal currentBalance;
    
    @Column(name = "color")
    private String color;
    
    @Column(name = "icon")
    private String icon;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(name = "active", nullable = false)
    private Boolean active = true;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constructors
    public BankAccount() {}

    public BankAccount(String name, String institution, AccountType type, BigDecimal initialBalance, User user) {
        this.name = name;
        this.institution = institution;
        this.type = type;
        this.initialBalance = initialBalance;
        this.currentBalance = initialBalance;
        this.user = user;
        this.active = true;
    }

    // Lifecycle methods
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (currentBalance == null) {
            currentBalance = initialBalance;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Business methods
    public void addBalance(BigDecimal amount) {
        this.currentBalance = this.currentBalance.add(amount);
    }

    public void subtractBalance(BigDecimal amount) {
        this.currentBalance = this.currentBalance.subtract(amount);
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

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankAccount that = (BankAccount) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", institution='" + institution + '\'' +
                ", type=" + type +
                ", currentBalance=" + currentBalance +
                ", active=" + active +
                '}';
    }

    // Enum
    public enum AccountType {
        CHECKING("Checking Account"),
        SAVINGS("Savings Account"),
        INVESTMENT("Investment Account"),
        CREDIT_CARD("Credit Card"),
        WALLET("Wallet");

        private final String description;

        AccountType(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
} 