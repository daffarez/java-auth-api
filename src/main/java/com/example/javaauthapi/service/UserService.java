package com.example.javaauthapi.service;

import com.example.javaauthapi.dto.request.ChangePasswordRequest;
import com.example.javaauthapi.dto.request.UpdateProfileRequest;
import com.example.javaauthapi.exception.DuplicateResourceException;
import com.example.javaauthapi.model.User;
import com.example.javaauthapi.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public void changePassword(String username, ChangePasswordRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getOldPassword(), request.getNewPassword())) {
            throw new RuntimeException("Password not matched");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    @Transactional
    public User updateProfile(String username, UpdateProfileRequest updateProfileRequest) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));


        if (!user.getEmail().equals(updateProfileRequest.getEmail()) &&
                userRepository.existsByEmail(updateProfileRequest.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        user.setFirstName(updateProfileRequest.getFirstName());
        user.setLastName(updateProfileRequest.getLastName());
        user.setEmail(updateProfileRequest.getEmail());

        return userRepository.save(user);
    }
}
