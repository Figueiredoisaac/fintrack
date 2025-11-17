package com.fintrack.dto;

import java.time.LocalDateTime;

public class AuthResponseDTO {
    
    private String token;
    private String type = "Bearer";
    private Long userId;
    private String userName;
    private String userEmail;
    private LocalDateTime expiresAt;
    
    // Construtores
    public AuthResponseDTO() {}
    
    public AuthResponseDTO(String token, Long userId, String userName, String userEmail, LocalDateTime expiresAt) {
        this.token = token;
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.expiresAt = expiresAt;
    }
    
    // Getters e Setters
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getUserEmail() {
        return userEmail;
    }
    
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    
    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }
    
    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }
} 