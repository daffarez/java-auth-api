package com.example.javaauthapi.repository;

import com.example.javaauthapi.model.RefreshToken;
import com.example.javaauthapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    void deleteByUser(User user);
}
