package com.fintrack.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fintrack.dto.LoginDTO;
import com.fintrack.dto.UserDTO;
import com.fintrack.entity.User;
import com.fintrack.repository.UserRepository;
import com.fintrack.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebMvc
@ActiveProfiles("test")
@Transactional
class AuthControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void register_ShouldCreateUserAndReturnToken() throws Exception {
        // Given
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Test User");
        userDTO.setEmail("test@fintrack.com");
        userDTO.setPassword("123456");

        // When & Then
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.type").value("Bearer"))
                .andExpect(jsonPath("$.userName").value("Test User"))
                .andExpect(jsonPath("$.userEmail").value("test@fintrack.com"));
    }

    @Test
    void register_WhenEmailExists_ShouldReturnBadRequest() throws Exception {
        // Given
        User existingUser = new User();
        existingUser.setName("Existing User");
        existingUser.setEmail("test@fintrack.com");
        existingUser.setPassword(passwordEncoder.encode("123456"));
        existingUser.setActive(true);
        userRepository.save(existingUser);

        UserDTO userDTO = new UserDTO();
        userDTO.setName("Test User");
        userDTO.setEmail("test@fintrack.com");
        userDTO.setPassword("123456");

        // When & Then
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void login_WhenValidCredentials_ShouldReturnToken() throws Exception {
        // Given
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@fintrack.com");
        user.setPassword(passwordEncoder.encode("123456"));
        user.setActive(true);
        userRepository.save(user);

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("test@fintrack.com");
        loginDTO.setPassword("123456");

        // When & Then
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.type").value("Bearer"))
                .andExpect(jsonPath("$.userName").value("Test User"))
                .andExpect(jsonPath("$.userEmail").value("test@fintrack.com"));
    }

    @Test
    void login_WhenInvalidCredentials_ShouldReturnUnauthorized() throws Exception {
        // Given
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail("test@fintrack.com");
        loginDTO.setPassword("wrongpassword");

        // When & Then
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void validateToken_WhenValidToken_ShouldReturnTrue() throws Exception {
        // Given
        String token = tokenProvider.generateToken("test@fintrack.com");

        // When & Then
        mockMvc.perform(post("/api/auth/validate")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void validateToken_WhenInvalidToken_ShouldReturnFalse() throws Exception {
        // When & Then
        mockMvc.perform(post("/api/auth/validate")
                .header("Authorization", "Bearer invalidToken"))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    void validateToken_WhenNoToken_ShouldReturnFalse() throws Exception {
        // When & Then
        mockMvc.perform(post("/api/auth/validate"))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }
} 