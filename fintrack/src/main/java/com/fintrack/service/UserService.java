package com.fintrack.service;

import com.fintrack.dto.user.UserCreateDTO;
import com.fintrack.dto.user.UserResponseDTO;
import com.fintrack.dto.user.UserUpdateDTO;
import com.fintrack.entity.User;
import com.fintrack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service layer for User entity operations.
 * Handles business logic, validation, and coordination between repository and controller.
 * Implements soft delete pattern and secure password handling.
 */
@Service
@Transactional
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    // READ Operations
    
    /**
     * Find all active users.
     * @return List of active users as DTOs
     */
    @Transactional(readOnly = true)
    public List<UserResponseDTO> findAllActive() {
        return userRepository.findAll().stream()
                .filter(user -> user.getActive())
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Find all users with pagination.
     * @param pageable Pagination parameters
     * @return Page of active users
     */
    @Transactional(readOnly = true)
    public Page<UserResponseDTO> findAllActive(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(this::convertToResponseDTO);
    }
    
    /**
     * Find user by ID.
     * @param id User ID
     * @return Optional UserResponseDTO
     */
    @Transactional(readOnly = true)
    public Optional<UserResponseDTO> findById(Long id) {
        return userRepository.findById(id)
                .filter(user -> user.getActive())
                .map(this::convertToResponseDTO);
    }
    
    /**
     * Find user by email (active users only).
     * @param email User email
     * @return Optional UserResponseDTO
     */
    @Transactional(readOnly = true)
    public Optional<UserResponseDTO> findByEmail(String email) {
        return userRepository.findActiveByEmail(email)
                .map(this::convertToResponseDTO);
    }
    
    // CREATE Operation
    
    /**
     * Create new user.
     * Validates email uniqueness and encrypts password.
     * @param userCreateDTO User creation data
     * @return Created user as ResponseDTO
     * @throws IllegalArgumentException if email already exists
     */
    public UserResponseDTO create(UserCreateDTO userCreateDTO) {
        // Validate email uniqueness
        if (userRepository.existsActiveByEmail(userCreateDTO.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + userCreateDTO.getEmail());
        }
        
        // Create and save user
        User user = convertCreateDTOToEntity(userCreateDTO);
        user.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));
        user.setActive(true);
        
        User savedUser = userRepository.save(user);
        return convertToResponseDTO(savedUser);
    }
    
    // UPDATE Operation
    
    /**
     * Update existing user.
     * Only updates provided fields (partial update).
     * @param id User ID
     * @param userUpdateDTO Update data
     * @return Updated user as ResponseDTO
     * @throws IllegalArgumentException if user not found or email conflict
     */
    public Optional<UserResponseDTO> update(Long id, UserUpdateDTO userUpdateDTO) {
        return userRepository.findById(id)
                .filter(user -> user.getActive())
                .map(existingUser -> {
                    // Validate email uniqueness if changing email
                    if (userUpdateDTO.getEmail() != null && 
                        !userUpdateDTO.getEmail().equals(existingUser.getEmail()) &&
                        userRepository.existsActiveByEmail(userUpdateDTO.getEmail())) {
                        throw new IllegalArgumentException("Email already exists: " + userUpdateDTO.getEmail());
                    }
                    
                    // Update only provided fields
                    updateUserFromDTO(existingUser, userUpdateDTO);
                    
                    User savedUser = userRepository.save(existingUser);
                    return convertToResponseDTO(savedUser);
                });
    }
    
    // DELETE Operation (Soft Delete)
    
    /**
     * Soft delete user by setting active = false.
     * Preserves data integrity and audit trail.
     * @param id User ID
     * @return true if user was deactivated, false if not found
     */
    public boolean softDelete(Long id) {
        return userRepository.findById(id)
                .filter(user -> user.getActive())
                .map(user -> {
                    user.setActive(false);
                    userRepository.save(user);
                    return true;
                })
                .orElse(false);
    }
    
    /**
     * Reactivate previously soft-deleted user.
     * @param id User ID
     * @return true if user was reactivated, false if not found
     */
    public boolean reactivate(Long id) {
        return userRepository.findById(id)
                .filter(user -> !user.getActive())
                .map(user -> {
                    user.setActive(true);
                    userRepository.save(user);
                    return true;
                })
                .orElse(false);
    }
    
    // VALIDATION Operations
    
    /**
     * Check if email exists among active users.
     * @param email Email to check
     * @return true if email exists
     */
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsActiveByEmail(email);
    }
    
    /**
     * Check if email is available for registration.
     * @param email Email to check
     * @return true if email is available
     */
    @Transactional(readOnly = true)
    public boolean isEmailAvailable(String email) {
        return !userRepository.existsActiveByEmail(email);
    }
    
    // CONVERSION Methods
    
    /**
     * Convert User entity to UserResponseDTO.
     * Excludes sensitive information like password.
     */
    private UserResponseDTO convertToResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setActive(user.getActive());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        return dto;
    }
    
    /**
     * Convert UserCreateDTO to User entity.
     */
    private User convertCreateDTOToEntity(UserCreateDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        // Password will be set separately after encoding
        return user;
    }
    
    /**
     * Update User entity from UserUpdateDTO.
     * Only updates non-null fields.
     */
    private void updateUserFromDTO(User user, UserUpdateDTO dto) {
        if (dto.getName() != null) {
            user.setName(dto.getName());
        }
        if (dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
        }
        if (dto.getPassword() != null && !dto.getPassword().trim().isEmpty()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        if (dto.getActive() != null) {
            user.setActive(dto.getActive());
        }
    }
} 