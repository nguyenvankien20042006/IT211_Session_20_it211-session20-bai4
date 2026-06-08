package com.example.bai4.repository;

import com.example.bai4.model.entity.StudentToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentTokenRepository extends JpaRepository<StudentToken, Long> {
    Optional<StudentToken> findByRefreshTokenValue(String refreshTokenValue);

    Optional<StudentToken> findByUsername(String username);

    @Modifying
    @Query("""
            update StudentToken studentToken
            set studentToken.isRevoked = true
            where studentToken.username = :username
            """)
    void revokeAllTokenByUsername(@Param("username") String username);
}
