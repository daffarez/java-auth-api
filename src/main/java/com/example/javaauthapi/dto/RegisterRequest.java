package com.example.javaauthapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank(message = "Username field is required")
    @Size(min = 4, max = 20, message = "Username must have 4-20 character")
    private String username;

    @NotBlank(message = "Password field is required")
    @Size(min = 8, message = "Password field is minimum 8 characters")
    private String password;
}
