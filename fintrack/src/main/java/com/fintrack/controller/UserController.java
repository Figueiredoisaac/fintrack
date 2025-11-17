package com.fintrack.controller;

import com.fintrack.dto.user.UserCreateDTO;
import com.fintrack.dto.user.UserResponseDTO;
import com.fintrack.dto.user.UserUpdateDTO;
import com.fintrack.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for User management.
 * Provides endpoints for CRUD operations on users following RESTful principles.
 * Implements proper HTTP status codes and error handling.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    private final UserService userService;
    
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    // READ Operations
    
    /**
     * Get all active users.
     * Supports pagination with default sorting by creation date.
     * 
     * @param pageable Pagination parameters (optional)
     * @return Page of active users
     */
    @GetMapping
    public ResponseEntity<Page<UserResponseDTO>> findAll(
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) 
            Pageable pageable) {
        Page<UserResponseDTO> users = userService.findAllActive(pageable);
        return ResponseEntity.ok(users);
    }
    
    /**
     * Get all active users without pagination.
     * Use with caution - can return large datasets.
     * 
     * @return List of all active users
     */
    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDTO>> findAllWithoutPagination() {
        List<UserResponseDTO> users = userService.findAllActive();
        return ResponseEntity.ok(users);
    }
    
    /**
     * Get user by ID.
     * 
     * @param id User ID
     * @return User data or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
        Optional<UserResponseDTO> user = userService.findById(id);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Get user by email.
     * 
     * @param email User email
     * @return User data or 404 if not found
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDTO> findByEmail(@PathVariable String email) {
        Optional<UserResponseDTO> user = userService.findByEmail(email);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    // CREATE Operation
    
    /**
     * Create new user.
     * Validates input data and checks email uniqueness.
     * 
     * @param userCreateDTO User creation data
     * @return Created user data with 201 status
     */
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody UserCreateDTO userCreateDTO) {
        try {
            UserResponseDTO createdUser = userService.create(userCreateDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    // UPDATE Operation
    
    /**
     * Update existing user.
     * Supports partial updates - only provided fields are updated.
     * 
     * @param id User ID
     * @param userUpdateDTO Update data
     * @return Updated user data or 404 if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, 
                                   @Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        try {
            Optional<UserResponseDTO> updatedUser = userService.update(id, userUpdateDTO);
            return updatedUser.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    // DELETE Operations
    
    /**
     * Soft delete user (deactivate).
     * Sets user.active = false instead of physically deleting.
     * 
     * @param id User ID
     * @return 204 No Content if successful, 404 if not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDelete(@PathVariable Long id) {
        boolean deleted = userService.softDelete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
    
    /**
     * Reactivate previously soft-deleted user.
     * Sets user.active = true.
     * 
     * @param id User ID
     * @return 200 OK if successful, 404 if not found
     */
    @PatchMapping("/{id}/reactivate")
    public ResponseEntity<UserResponseDTO> reactivate(@PathVariable Long id) {
        boolean reactivated = userService.reactivate(id);
        if (reactivated) {
            // Return updated user data
            Optional<UserResponseDTO> user = userService.findById(id);
            return user.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }
        return ResponseEntity.notFound().build();
    }
    
    // VALIDATION Operations
    
    /**
     * Check if email exists among active users.
     * Useful for frontend validation.
     * 
     * @param email Email to check
     * @return true if email exists
     */
    @GetMapping("/check-email/{email}")
    public ResponseEntity<Boolean> checkEmailExists(@PathVariable String email) {
        boolean exists = userService.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }
    
    /**
     * Check if email is available for registration.
     * Inverse of checkEmailExists for better UX.
     * 
     * @param email Email to check
     * @return true if email is available
     */
    @GetMapping("/email-available/{email}")
    public ResponseEntity<Boolean> isEmailAvailable(@PathVariable String email) {
        boolean available = userService.isEmailAvailable(email);
        return ResponseEntity.ok(available);
    }
    
    // Utility class for error responses
    private static class Map {
        public static java.util.Map<String, String> of(String key, String value) {
            return java.util.Collections.singletonMap(key, value);
        }
    }
} 