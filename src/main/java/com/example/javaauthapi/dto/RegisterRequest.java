package com.example.javaauthapi.dto;

import com.example.javaauthapi.model.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank(message = "username field is required")
    @Size(min = 4, max = 20, message = "username must have 4-20 character")
    private String username;

    @NotBlank(message = "password field is required")
    @Size(min = 8, message = "password field is minimum 8 characters")
    private String password;

    private Role role;
}
