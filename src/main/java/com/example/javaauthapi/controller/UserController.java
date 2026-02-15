package com.example.javaauthapi.controller;

import com.example.javaauthapi.dto.UserResponse;
import com.example.javaauthapi.security.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/profile")
    public ResponseEntity<?> getMyProfile(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Token not found!");
        }

        String token = authHeader.substring(7);
        Claims claims = jwtUtil.extractAllClaims(token);

        UserResponse response = UserResponse.builder()
                .id(claims.get("id", Long.class))
                .username(claims.getSubject())
                .role(claims.get("role", String.class))
                .createdAt(Instant.ofEpochSecond(claims.get("createdAt", Long.class)))
                .build();

        return ResponseEntity.ok(response);
    }
}
