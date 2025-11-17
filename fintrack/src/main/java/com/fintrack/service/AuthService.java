package com.fintrack.service;

import com.fintrack.dto.AuthResponseDTO;
import com.fintrack.dto.LoginDTO;
import com.fintrack.dto.UserDTO;
import com.fintrack.entity.User;
import com.fintrack.repository.UserRepository;
import com.fintrack.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    public AuthResponseDTO login(LoginDTO loginDTO) {
        // Authenticate user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate JWT token
        String token = tokenProvider.generateToken(authentication);
        Date expirationDate = tokenProvider.getExpirationDateFromToken(token);

        // Get user details
        User user = userRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new AuthResponseDTO(
                token,
                user.getId(),
                user.getName(),
                user.getEmail(),
                LocalDateTime.of(expirationDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate(),
                        expirationDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalTime())
        );
    }

    public AuthResponseDTO register(UserDTO userDTO) {
        // Check if email already exists
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new RuntimeException("Email is already in use");
        }

        // Create new user
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setActive(true);

        user = userRepository.save(user);

        // Generate JWT token
        String token = tokenProvider.generateToken(userDTO.getEmail());
        Date expirationDate = tokenProvider.getExpirationDateFromToken(token);

        return new AuthResponseDTO(
                token,
                user.getId(),
                user.getName(),
                user.getEmail(),
                LocalDateTime.of(expirationDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate(),
                        expirationDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalTime())
        );
    }

    public boolean validateToken(String token) {
        return tokenProvider.validateToken(token);
    }

    public String getEmailFromToken(String token) {
        return tokenProvider.getEmailFromToken(token);
    }
} 