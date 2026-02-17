package com.example.javaauthapi.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    @Schema(example = "eyJhbGciOiJIUzI1NiJ9...")
    private String accessToken;

    @Schema(example = "550e8400-e29b-41d4-a716-446655440000")
    private String refreshToken;

    @Schema(example = "johndoe")
    private String username;
}
