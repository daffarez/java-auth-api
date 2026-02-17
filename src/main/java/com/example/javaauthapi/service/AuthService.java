package com.example.javaauthapi.service;

import com.example.javaauthapi.dto.request.RegisterRequest;
import com.example.javaauthapi.exception.DuplicateResourceException;
import com.example.javaauthapi.model.Role;
import com.example.javaauthapi.model.User;
import com.example.javaauthapi.repository.UserRepository;
import com.example.javaauthapi.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public User register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateResourceException("Username already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        User newUser = new User();

        newUser.setUsername(request.getUsername());
        newUser.setEmail(request.getEmail());
        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getLastName());

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        newUser.setPassword(encodedPassword);

        newUser.setRole(request.getRole() == null ? Role.ROLE_USER : request.getRole());

        newUser.setCreatedAt(Instant.now());

        return userRepository.save(newUser);
    }

    public String login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Incorrect password");
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole());
        claims.put("id", user.getId());
        claims.put("createdAt", user.getCreatedAt().toEpochMilli());


        return jwtUtil.generateToken(
                user.getUsername(),
                claims
        );
    }
}
