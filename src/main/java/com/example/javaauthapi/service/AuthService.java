package com.example.javaauthapi.service;

import com.example.javaauthapi.dto.RegisterRequest;
import com.example.javaauthapi.model.Role;
import com.example.javaauthapi.model.User;
import com.example.javaauthapi.repository.UserRepository;
import com.example.javaauthapi.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public User register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("username exist.");
        }

        User newUser = new User();

        newUser.setUsername(request.getUsername());

        String encodedPassword = bCryptPasswordEncoder.encode(request.getPassword());
        newUser.setPassword(encodedPassword);

        if (request.getRole() == null) {
            newUser.setRole(Role.ROLE_USER);
        }

        newUser.setCreatedAt(Instant.now());

        return userRepository.save(newUser);
    }

    public String login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("user not found."));

        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("wrong password");
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole());
        claims.put("id", user.getId());
        claims.put("createdAt", user.getCreatedAt());


        return jwtUtil.generateToken(
                user.getUsername(),
                claims
        );
    }
}
