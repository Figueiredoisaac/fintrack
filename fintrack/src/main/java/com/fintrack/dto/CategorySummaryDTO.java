package com.fintrack.dto;

import java.math.BigDecimal;

public class CategorySummaryDTO {
    
    private Long categoryId;
    private String categoryName;
    private String categoryType;
    private BigDecimal totalAmount;
    private Long transactionCount;
    private String color;
    private String icon;
    private BigDecimal percentage;
    
    // Construtores
    public CategorySummaryDTO() {}
    
    public CategorySummaryDTO(Long categoryId, String categoryName, String categoryType,
                            BigDecimal totalAmount, Long transactionCount, String color, String icon) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryType = categoryType;
        this.totalAmount = totalAmount;
        this.transactionCount = transactionCount;
        this.color = color;
        this.icon = icon;
    }
    
    // Getters e Setters
    public Long getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
    
    public String getCategoryName() {
        return categoryName;
    }
    
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    public String getCategoryType() {
        return categoryType;
    }
    
    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }
    
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
    
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    public Long getTransactionCount() {
        return transactionCount;
    }
    
    public void setTransactionCount(Long transactionCount) {
        this.transactionCount = transactionCount;
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
    
    public BigDecimal getPercentage() {
        return percentage;
    }
    
    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }
} 