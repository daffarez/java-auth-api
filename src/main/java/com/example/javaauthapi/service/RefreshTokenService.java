package com.example.javaauthapi.service;

import com.example.javaauthapi.dto.response.LoginResponse;
import com.example.javaauthapi.model.RefreshToken;
import com.example.javaauthapi.model.User;
import com.example.javaauthapi.repository.RefreshTokenRepository;
import com.example.javaauthapi.repository.UserRepository;
import com.example.javaauthapi.security.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public RefreshToken createRefreshToken(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        refreshTokenRepository.deleteByUser(user);

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plus(7, ChronoUnit.DAYS))
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

    private String generateToken(String username) {
        return jwtUtil.generateToken(username, new HashMap<>());
    }

    @Transactional
    public LoginResponse refreshAccessToken(String requestToken) {
        return refreshTokenRepository.findByToken(requestToken)
                .map(this::verifyExpiration)
                .map(token -> {
                    User user = token.getUser();
                    String newAccessToken = generateToken(user.getUsername());
                    return new LoginResponse(newAccessToken, requestToken, user.getUsername());
                })
                .orElseThrow(() -> new RuntimeException("Refresh token is not in database!"));
    }
}
