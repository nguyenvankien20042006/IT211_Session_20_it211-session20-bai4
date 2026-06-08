package com.example.bai4.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "student_tokens")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StudentToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String refreshTokenValue;
    private Boolean isRevoked;
    private Date isExpired;
    private String username;
}
