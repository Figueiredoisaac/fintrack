package com.fintrack.service;

import com.fintrack.dto.AuthResponseDTO;
import com.fintrack.dto.LoginDTO;
import com.fintrack.dto.UserDTO;
import com.fintrack.entity.User;
import com.fintrack.repository.UserRepository;
import com.fintrack.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider tokenProvider;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthService authService;

    private User testUser;
    private LoginDTO loginDTO;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setName("Test User");
        testUser.setEmail("test@fintrack.com");
        testUser.setPassword("encodedPassword");
        testUser.setActive(true);

        loginDTO = new LoginDTO();
        loginDTO.setEmail("test@fintrack.com");
        loginDTO.setPassword("123456");

        userDTO = new UserDTO();
        userDTO.setName("Test User");
        userDTO.setEmail("test@fintrack.com");
        userDTO.setPassword("123456");
    }

    @Test
    void login_ShouldReturnAuthResponse() {
        // Given
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(userRepository.findByEmail("test@fintrack.com")).thenReturn(Optional.of(testUser));
        when(tokenProvider.generateToken(authentication)).thenReturn("jwtToken");
        when(tokenProvider.getExpirationDateFromToken("jwtToken")).thenReturn(new Date());

        // When
        AuthResponseDTO result = authService.login(loginDTO);

        // Then
        assertNotNull(result);
        assertEquals("jwtToken", result.getToken());
        assertEquals("Bearer", result.getType());
        assertEquals(testUser.getId(), result.getUserId());
        assertEquals(testUser.getName(), result.getUserName());
        assertEquals(testUser.getEmail(), result.getUserEmail());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository).findByEmail("test@fintrack.com");
        verify(tokenProvider).generateToken(authentication);
    }

    @Test
    void login_WhenUserNotFound_ShouldThrowException() {
        // Given
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(userRepository.findByEmail("test@fintrack.com")).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> authService.login(loginDTO));
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository).findByEmail("test@fintrack.com");
    }

    @Test
    void register_ShouldCreateUserAndReturnAuthResponse() {
        // Given
        when(userRepository.existsByEmail("test@fintrack.com")).thenReturn(false);
        when(passwordEncoder.encode("123456")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(tokenProvider.generateToken("test@fintrack.com")).thenReturn("jwtToken");
        when(tokenProvider.getExpirationDateFromToken("jwtToken")).thenReturn(new Date());

        // When
        AuthResponseDTO result = authService.register(userDTO);

        // Then
        assertNotNull(result);
        assertEquals("jwtToken", result.getToken());
        assertEquals("Bearer", result.getType());
        assertEquals(testUser.getId(), result.getUserId());
        assertEquals(testUser.getName(), result.getUserName());
        assertEquals(testUser.getEmail(), result.getUserEmail());
        verify(userRepository).existsByEmail("test@fintrack.com");
        verify(passwordEncoder).encode("123456");
        verify(userRepository).save(any(User.class));
        verify(tokenProvider).generateToken("test@fintrack.com");
    }

    @Test
    void register_WhenEmailExists_ShouldThrowException() {
        // Given
        when(userRepository.existsByEmail("test@fintrack.com")).thenReturn(true);

        // When & Then
        assertThrows(RuntimeException.class, () -> authService.register(userDTO));
        verify(userRepository).existsByEmail("test@fintrack.com");
        verify(userRepository, never()).save(any());
    }

    @Test
    void validateToken_ShouldReturnTrue() {
        // Given
        String token = "validToken";
        when(tokenProvider.validateToken(token)).thenReturn(true);

        // When
        boolean result = authService.validateToken(token);

        // Then
        assertTrue(result);
        verify(tokenProvider).validateToken(token);
    }

    @Test
    void validateToken_ShouldReturnFalse() {
        // Given
        String token = "invalidToken";
        when(tokenProvider.validateToken(token)).thenReturn(false);

        // When
        boolean result = authService.validateToken(token);

        // Then
        assertFalse(result);
        verify(tokenProvider).validateToken(token);
    }

    @Test
    void getEmailFromToken_ShouldReturnEmail() {
        // Given
        String token = "validToken";
        when(tokenProvider.getEmailFromToken(token)).thenReturn("test@fintrack.com");

        // When
        String result = authService.getEmailFromToken(token);

        // Then
        assertEquals("test@fintrack.com", result);
        verify(tokenProvider).getEmailFromToken(token);
    }
} 