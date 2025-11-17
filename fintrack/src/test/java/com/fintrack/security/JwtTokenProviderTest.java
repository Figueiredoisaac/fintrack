package com.fintrack.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtTokenProviderTest {

    @InjectMocks
    private JwtTokenProvider tokenProvider;

    private static final String TEST_SECRET = "TestSecretKeyForTestingOnly123456789012345678901234567890123456789012345678901234567890MoreCharactersToMakeItSecure123456789012345678901234567890";
    private static final int TEST_EXPIRATION = 3600000; // 1 hour

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(tokenProvider, "jwtSecret", TEST_SECRET);
        ReflectionTestUtils.setField(tokenProvider, "jwtExpirationMs", TEST_EXPIRATION);
    }

    @Test
    void generateToken_WithAuthentication_ShouldReturnValidToken() {
        // Given
        UserDetails userDetails = User.withUsername("test@fintrack.com")
                .password("password")
                .authorities(new ArrayList<>())
                .build();
        
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

        // When
        String token = tokenProvider.generateToken(authentication);

        // Then
        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(tokenProvider.validateToken(token));
        assertEquals("test@fintrack.com", tokenProvider.getEmailFromToken(token));
    }

    @Test
    void generateToken_WithEmail_ShouldReturnValidToken() {
        // Given
        String email = "test@fintrack.com";

        // When
        String token = tokenProvider.generateToken(email);

        // Then
        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(tokenProvider.validateToken(token));
        assertEquals(email, tokenProvider.getEmailFromToken(token));
    }

    @Test
    void getEmailFromToken_WithValidToken_ShouldReturnEmail() {
        // Given
        String email = "test@fintrack.com";
        String token = tokenProvider.generateToken(email);

        // When
        String result = tokenProvider.getEmailFromToken(token);

        // Then
        assertEquals(email, result);
    }

    @Test
    void getExpirationDateFromToken_WithValidToken_ShouldReturnDate() {
        // Given
        String email = "test@fintrack.com";
        String token = tokenProvider.generateToken(email);

        // When
        Date expirationDate = tokenProvider.getExpirationDateFromToken(token);

        // Then
        assertNotNull(expirationDate);
        assertTrue(expirationDate.after(new Date()));
    }

    @Test
    void validateToken_WithValidToken_ShouldReturnTrue() {
        // Given
        String email = "test@fintrack.com";
        String token = tokenProvider.generateToken(email);

        // When
        boolean result = tokenProvider.validateToken(token);

        // Then
        assertTrue(result);
    }

    @Test
    void validateToken_WithInvalidToken_ShouldReturnFalse() {
        // Given
        String invalidToken = "invalid.token.here";

        // When
        boolean result = tokenProvider.validateToken(invalidToken);

        // Then
        assertFalse(result);
    }

    @Test
    void validateToken_WithEmptyToken_ShouldReturnFalse() {
        // Given
        String emptyToken = "";

        // When
        boolean result = tokenProvider.validateToken(emptyToken);

        // Then
        assertFalse(result);
    }

    @Test
    void validateToken_WithNullToken_ShouldReturnFalse() {
        // Given
        String nullToken = null;

        // When
        boolean result = tokenProvider.validateToken(nullToken);

        // Then
        assertFalse(result);
    }

    @Test
    void isTokenExpired_WithValidToken_ShouldReturnFalse() {
        // Given
        String email = "test@fintrack.com";
        String token = tokenProvider.generateToken(email);

        // When
        boolean result = tokenProvider.isTokenExpired(token);

        // Then
        assertFalse(result);
    }

    @Test
    void generateToken_ShouldIncludeExpirationTime() {
        // Given
        String email = "test@fintrack.com";
        Date beforeGeneration = new Date();

        // When
        String token = tokenProvider.generateToken(email);
        Date expirationDate = tokenProvider.getExpirationDateFromToken(token);

        // Then
        assertTrue(expirationDate.after(beforeGeneration));
        assertTrue(expirationDate.after(new Date()));
    }
} 