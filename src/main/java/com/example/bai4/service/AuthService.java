package com.example.bai4.service;

import com.example.bai4.model.dto.request.AuthRequest;
import com.example.bai4.model.dto.response.JWTResponse;
import com.example.bai4.model.entity.Student;
import com.example.bai4.model.entity.StudentToken;
import com.example.bai4.repository.StudentRepository;
import com.example.bai4.repository.StudentTokenRepository;
import com.example.bai4.security.jwt.JWTProvider;
import com.example.bai4.security.principal.StudentPrincipal;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final StudentRepository studentRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTProvider jWTProvider;
    private final StudentTokenService studentTokenService;
    private final PasswordEncoder passwordEncoder;
    private final StudentTokenRepository studentTokenRepository;

    public Student register(AuthRequest authRequest) {
        Student student = Student.builder()
                .email(authRequest.getEmail())
                .password(passwordEncoder.encode(authRequest.getPassword()))
                .isActive(true)
                .build();
        return studentRepository.save(student);
    }

    public JWTResponse login(AuthRequest authRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                authRequest.getEmail(),
                authRequest.getPassword()
        );
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        String accessToken = jWTProvider.generateToken(authRequest.getEmail());
        StudentToken refreshToken = studentTokenService.generateTokenSession(authRequest.getEmail());
        return JWTResponse.builder().accessToken(accessToken).refreshToken(refreshToken.getRefreshTokenValue()).build();
    }

    @Transactional
    public void logout() {
        StudentPrincipal studentPrincipal = (StudentPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = studentPrincipal.getUsername();
        studentTokenRepository.revokeAllTokenByUsername(username);
    }
}
