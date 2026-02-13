package com.example.javaauthapi.controller;

import com.example.javaauthapi.model.LoginRequest;
import com.example.javaauthapi.model.User;
import com.example.javaauthapi.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User registeredUser = authService.register(user);
            return ResponseEntity.status(201).body(registeredUser.getUsername() + " successfully registered.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public  ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            String response = authService.login(loginRequest.getUsername(), loginRequest.getPassword());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
}