package com.example.javaauthapi.service;

import com.example.javaauthapi.dto.response.LoginResponse;
import com.example.javaauthapi.model.RefreshToken;
import com.example.javaauthapi.model.Role;
import com.example.javaauthapi.model.User;
import com.example.javaauthapi.repository.RefreshTokenRepository;
import com.example.javaauthapi.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RefreshTokenServiceTest {
    @Mock
    private RefreshTokenRepository refreshTokenRepository;
    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private RefreshTokenService refreshTokenService;

    @Test
    void testRefreshAccessToken_Success() {
        String rawToken = "uuid-token";
        User user = new User();
        user.setUsername("test_user");
        user.setRole(Role.ROLE_USER);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(rawToken);
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(Instant.now().plusSeconds(3600));

        when(refreshTokenRepository.findByToken(rawToken)).thenReturn(Optional.of(refreshToken));
        when(jwtUtil.generateToken(anyString(), anyMap())).thenReturn("new-access-token");

        LoginResponse response = refreshTokenService.refreshAccessToken(rawToken);

        assertNotNull(response);
        assertEquals("new-access-token", response.getAccessToken());
        assertEquals(rawToken, response.getRefreshToken());
    }
}
