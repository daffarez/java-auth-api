package com.example.javaauthapi.repository;

import com.example.javaauthapi.model.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, Long> {
    boolean existsByToken(String token);

    void deleteByExpiryDateBefore(java.time.Instant now);
}
