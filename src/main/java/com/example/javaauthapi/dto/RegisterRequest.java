package com.example.javaauthapi.dto;

import com.example.javaauthapi.model.Role;
import jakarta.validation.constraints.Email;
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

    private Role role;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "First name is required")
    private String firstName;

    private String lastName;
}
