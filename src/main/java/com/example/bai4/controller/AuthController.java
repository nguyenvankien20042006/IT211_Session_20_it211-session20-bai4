package com.example.bai4.controller;

import com.example.bai4.model.dto.request.AuthRequest;
import com.example.bai4.model.dto.request.RefreshTokenRequest;
import com.example.bai4.model.dto.response.JWTResponse;
import com.example.bai4.model.entity.Student;
import com.example.bai4.model.entity.StudentToken;
import com.example.bai4.service.AuthService;
import com.example.bai4.service.StudentTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/elearning/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final StudentTokenService studentTokenService;

    @PostMapping("/register")
    public ResponseEntity<Student> register(@RequestBody AuthRequest authRequest) {
        return new ResponseEntity<>(authService.register(authRequest), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JWTResponse> login(@RequestBody AuthRequest authRequest) {
        return new ResponseEntity<>(authService.login(authRequest), HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JWTResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        return new ResponseEntity<>(studentTokenService.refreshToken(request.getToken()), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<StudentToken> logout() {
        authService.logout();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
