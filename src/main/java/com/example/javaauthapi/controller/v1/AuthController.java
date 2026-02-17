package com.example.javaauthapi.controller.v1;

import com.example.javaauthapi.dto.request.LoginRequest;
import com.example.javaauthapi.dto.request.RegisterRequest;
import com.example.javaauthapi.dto.response.LoginResponse;
import com.example.javaauthapi.dto.response.RegisterResponse;
import com.example.javaauthapi.model.RefreshToken;
import com.example.javaauthapi.model.User;
import com.example.javaauthapi.service.AuthService;
import com.example.javaauthapi.service.RefreshTokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("v1/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequst) {
        try {
            User registeredUser = authService.register(registerRequst);
            return ResponseEntity.status(HttpStatus.CREATED).body(RegisterResponse.<Map<String, String>>builder()
                    .message(registerRequst.getUsername() + " successfully registered")
                    .data(Map.of(
                            "email", registeredUser.getEmail(),
                            "role", registeredUser.getRole().toString()
                    ))
                    .build());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            String accessToken = authService.login(loginRequest.getUsername(), loginRequest.getPassword());
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(loginRequest.getUsername());
            LoginResponse response = new LoginResponse(
                    accessToken,
                    refreshToken.getToken(),
                    loginRequest.getUsername()
            );

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "error", e.getMessage()
            ));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(@RequestHeader("Authorization") String authHeader) {
        authService.logout(authHeader);
        return ResponseEntity.ok(Map.of(
                "message", "Logged out successfully"
        ));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
        try {
            String requestToken = request.get("refreshToken");
            LoginResponse response = refreshTokenService.refreshAccessToken(requestToken);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                    "error", e.getMessage()
            ));
        }
    }
}