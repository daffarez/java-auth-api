package com.example.javaauthapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String username;
    private String role;
    private Instant createdAt;
}