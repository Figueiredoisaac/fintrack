package com.fintrack.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

/**
 * DTO for updating existing users.
 * All fields are optional - only provided fields will be updated.
 * Password is optional and will be encrypted if provided.
 */
public class UserUpdateDTO {
    
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;
    
    @Email(message = "Email must be valid")
    @Size(max = 254, message = "Email must not exceed 254 characters")
    private String email;
    
    @Size(min = 6, max = 128, message = "Password must be between 6 and 128 characters")
    private String password;
    
    private Boolean active;

    // Constructors
    public UserUpdateDTO() {}

    public UserUpdateDTO(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "UserUpdateDTO{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", active=" + active +
                '}';
    }
} 