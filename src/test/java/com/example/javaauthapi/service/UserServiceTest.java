package com.example.javaauthapi.service;

import com.example.javaauthapi.dto.request.UpdateProfileRequest;
import com.example.javaauthapi.model.User;
import com.example.javaauthapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {


    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User dummyUser;

    @BeforeEach
    void setUp() {
        dummyUser = new User();
        dummyUser.setUsername("user_test");
        dummyUser.setPassword("encoded_password");
        dummyUser.setEmail("test@mail.com");
    }

    @Test
    void testUpdateProfile_Success() {
        String username = "user_test";
        UpdateProfileRequest request = new UpdateProfileRequest();
        request.setFirstName("New");
        request.setLastName("Name");
        request.setEmail("new@mail.com");

        User existingUser = new User();
        existingUser.setUsername(username);
        existingUser.setEmail("old@mail.com");
        existingUser.setUpdatedAt(Instant.now());

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(existingUser));
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        User result = userService.updateProfile(username, request);

        assertEquals("New", result.getFirstName());
        assertEquals("new@mail.com", result.getEmail());
        assertNotNull(result.getUpdatedAt());
    }
}
