package com.example.javaauthapi.controller;

import com.example.javaauthapi.dto.LoginRequest;
import com.example.javaauthapi.dto.RegisterRequest;
import com.example.javaauthapi.model.User;
import com.example.javaauthapi.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid  @RequestBody RegisterRequest registerRequst) {
        try {
            User registeredUser = authService.register(registerRequst);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "message", registeredUser.getUsername() + " successfully registered."
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public  ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            String token = authService.login(loginRequest.getUsername(), loginRequest.getPassword());
            return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                    "userToken", token
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
}