package com.example.bai4.service;

import com.example.bai4.model.dto.response.JWTResponse;
import com.example.bai4.model.entity.StudentToken;
import com.example.bai4.repository.StudentTokenRepository;
import com.example.bai4.security.jwt.JWTProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentTokenService {
    @Value("${jwt.refresh-expiration}")
    private Long refreshExpiration;
    private final StudentTokenRepository studentTokenRepository;
    private final JWTProvider jWTProvider;

    public StudentToken generateTokenSession(String username) {
        StudentToken studentToken = StudentToken.builder()
                .refreshTokenValue(UUID.randomUUID().toString())
                .username(username)
                .isExpired(new Date(new Date().getTime() + refreshExpiration))
                .isRevoked(false)
                .build();
        return studentTokenRepository.save(studentToken);
    }

    public StudentToken getTokenSession(String token) {
        return studentTokenRepository.findByRefreshTokenValue(token).orElseThrow(() -> new RuntimeException("Token not found"));
    }

    public boolean verifyToken(String token) {
        StudentToken studentToken = getTokenSession(token);
        if (studentToken.getIsRevoked() || studentToken.getIsExpired().before(new Date())) {
            return false;
        }
        return true;
    }

    public StudentToken revokeToken(String token) {
        StudentToken studentToken = getTokenSession(token);
        return studentTokenRepository.save(studentToken);
    }

    public JWTResponse refreshToken(String token) {
        StudentToken studentToken = getTokenSession(token);
        if (!verifyToken(token)) {
            throw new RuntimeException("Invalid refresh token");
        }
        String newToken = jWTProvider.generateToken(studentToken.getUsername());
        return JWTResponse.builder().accessToken(newToken).refreshToken(token).build();
    }
}
