package com.example.javaauthapi.controller.v1;

import com.example.javaauthapi.dto.ChangePasswordRequest;
import com.example.javaauthapi.dto.UserResponse;
import com.example.javaauthapi.security.JwtUtil;
import com.example.javaauthapi.service.UserService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.Instant;

@RestController
@RequestMapping("v1/api/users")
@RequiredArgsConstructor
public class UserController {

    private final JwtUtil jwtUtil;
    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getMyProfile(HttpServletRequest request) {
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

    @GetMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @Valid @RequestBody ChangePasswordRequest request,
            Principal principal
    ) {
        userService.changePassword(principal.getName(), request);
        return ResponseEntity.ok("Password successfully changed");
    }
}
