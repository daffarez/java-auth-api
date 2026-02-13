package com.example.javaauthapi.controller;

import com.example.javaauthapi.security.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/profile")
    public ResponseEntity<?> getMyProfile(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Token tidak ditemukan!");
        }

        String token = authHeader.substring(7);
        Claims claims = jwtUtil.extractAllClaims(token);

        Map<String, Object> response = new HashMap<>();
        response.put("id", claims.get("id"));
        response.put("username", claims.getSubject());
        response.put("role", claims.get("role"));

        Object createdAtRaw = claims.get("createdAt");
        response.put("createdAt", createdAtRaw);

        return ResponseEntity.ok(response);
    }
}
