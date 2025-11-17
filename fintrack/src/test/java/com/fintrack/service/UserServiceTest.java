package com.fintrack.service;

import com.fintrack.dto.UserDTO;
import com.fintrack.entity.User;
import com.fintrack.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private UserDTO testUserDTO;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setName("Test User");
        testUser.setEmail("test@fintrack.com");
        testUser.setPassword("encodedPassword");
        testUser.setActive(true);

        testUserDTO = new UserDTO();
        testUserDTO.setName("Test User");
        testUserDTO.setEmail("test@fintrack.com");
        testUserDTO.setPassword("123456");
    }

    @Test
    void findAll_ShouldReturnAllUsers() {
        // Given
        List<User> users = Arrays.asList(testUser);
        when(userRepository.findAll()).thenReturn(users);

        // When
        List<UserDTO> result = userService.findAll();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testUser.getName(), result.get(0).getName());
        assertEquals(testUser.getEmail(), result.get(0).getEmail());
        verify(userRepository).findAll();
    }

    @Test
    void findById_WhenUserExists_ShouldReturnUser() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        // When
        Optional<UserDTO> result = userService.findById(1L);

        // Then
        assertTrue(result.isPresent());
        assertEquals(testUser.getName(), result.get().getName());
        assertEquals(testUser.getEmail(), result.get().getEmail());
        verify(userRepository).findById(1L);
    }

    @Test
    void findById_WhenUserNotExists_ShouldReturnEmpty() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // When
        Optional<UserDTO> result = userService.findById(1L);

        // Then
        assertFalse(result.isPresent());
        verify(userRepository).findById(1L);
    }

    @Test
    void findByEmail_WhenUserExists_ShouldReturnUser() {
        // Given
        when(userRepository.findByEmail("test@fintrack.com")).thenReturn(Optional.of(testUser));

        // When
        Optional<UserDTO> result = userService.findByEmail("test@fintrack.com");

        // Then
        assertTrue(result.isPresent());
        assertEquals(testUser.getName(), result.get().getName());
        assertEquals(testUser.getEmail(), result.get().getEmail());
        verify(userRepository).findByEmail("test@fintrack.com");
    }

    @Test
    void create_ShouldCreateUserSuccessfully() {
        // Given
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // When
        UserDTO result = userService.create(testUserDTO);

        // Then
        assertNotNull(result);
        assertEquals(testUser.getName(), result.getName());
        assertEquals(testUser.getEmail(), result.getEmail());
        verify(passwordEncoder).encode(testUserDTO.getPassword());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void update_WhenUserExists_ShouldUpdateUser() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // When
        Optional<UserDTO> result = userService.update(1L, testUserDTO);

        // Then
        assertTrue(result.isPresent());
        assertEquals(testUser.getName(), result.get().getName());
        verify(userRepository).findById(1L);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void update_WhenUserNotExists_ShouldReturnEmpty() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // When
        Optional<UserDTO> result = userService.update(1L, testUserDTO);

        // Then
        assertFalse(result.isPresent());
        verify(userRepository).findById(1L);
        verify(userRepository, never()).save(any());
    }

    @Test
    void delete_WhenUserExists_ShouldReturnTrue() {
        // Given
        when(userRepository.existsById(1L)).thenReturn(true);

        // When
        boolean result = userService.delete(1L);

        // Then
        assertTrue(result);
        verify(userRepository).existsById(1L);
        verify(userRepository).deleteById(1L);
    }

    @Test
    void delete_WhenUserNotExists_ShouldReturnFalse() {
        // Given
        when(userRepository.existsById(1L)).thenReturn(false);

        // When
        boolean result = userService.delete(1L);

        // Then
        assertFalse(result);
        verify(userRepository).existsById(1L);
        verify(userRepository, never()).deleteById(any());
    }

    @Test
    void existsByEmail_WhenEmailExists_ShouldReturnTrue() {
        // Given
        when(userRepository.existsByEmail("test@fintrack.com")).thenReturn(true);

        // When
        boolean result = userService.existsByEmail("test@fintrack.com");

        // Then
        assertTrue(result);
        verify(userRepository).existsByEmail("test@fintrack.com");
    }

    @Test
    void existsByEmail_WhenEmailNotExists_ShouldReturnFalse() {
        // Given
        when(userRepository.existsByEmail("test@fintrack.com")).thenReturn(false);

        // When
        boolean result = userService.existsByEmail("test@fintrack.com");

        // Then
        assertFalse(result);
        verify(userRepository).existsByEmail("test@fintrack.com");
    }
} 