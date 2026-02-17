package com.example.javaauthapi.service;

import com.example.javaauthapi.dto.request.RegisterRequest;
import com.example.javaauthapi.exception.DuplicateResourceException;
import com.example.javaauthapi.model.InvalidatedToken;
import com.example.javaauthapi.model.Role;
import com.example.javaauthapi.model.User;
import com.example.javaauthapi.repository.InvalidatedTokenRepository;
import com.example.javaauthapi.repository.UserRepository;
import com.example.javaauthapi.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private InvalidatedTokenRepository invalidatedTokenRepository;

    @InjectMocks
    private AuthService authService;


    private User dummyUser;

    @BeforeEach
    void setUp() {
        authService = new AuthService(
                jwtUtil,
                userRepository,
                passwordEncoder,
                invalidatedTokenRepository
        );

        dummyUser = User.builder()
                .username("user_test")
                .password("encoded_password")
                .role(Role.ROLE_ADMIN)
                .createdAt(Instant.now())
                .build();
    }

    @Test
    void testRegister_Success() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("user_test");
        request.setPassword("password123");

        when(userRepository.existsByUsername("user_test")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encoded_password");
        when(userRepository.save(any(User.class))).thenReturn(dummyUser);

        User result = authService.register(request);

        assertNotNull(result);
        assertEquals("user_test", result.getUsername());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testLogin_Success() {
        String userTest = "user_test";
        String passTest = "password123";
        String hashTest = "encoded_password";

        when(userRepository.findByUsername(userTest)).thenReturn(Optional.of(dummyUser));
        lenient().when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        when(jwtUtil.generateToken(anyString(), anyMap())).thenReturn("invalid-token");

        String result = authService.login(userTest, passTest);

        assertNotNull(result);
        assertEquals("invalid-token", result);
    }

    @Test
    void testLogin_InvalidPassword() {
        when(userRepository.findByUsername("user_test")).thenReturn(Optional.of(dummyUser));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        assertThrows(RuntimeException.class, () -> authService.login("user_test", "wrong_password"));
    }

    @Test
    void testRegister_EmailAlreadyExists() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("existing@mail.com");

        // Mocking email check
        when(userRepository.existsByEmail("existing@mail.com")).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> authService.register(request));
    }

    @Test
    void testLogout_Success() {
        String authHeader = "Bearer valid_token";
        Instant now = Instant.now();

        when(jwtUtil.extractExpiration(anyString())).thenReturn(now);

        authService.logout(authHeader);

        verify(invalidatedTokenRepository, times(1)).save(any(InvalidatedToken.class));
    }
}