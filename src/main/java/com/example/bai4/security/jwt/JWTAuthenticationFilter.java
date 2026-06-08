package com.example.bai4.security.jwt;

import com.example.bai4.security.principal.StudentPrincipal;
import com.example.bai4.security.principal.StudentPrincipalService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Builder
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private final JWTProvider jWTProvider;
    private final StudentPrincipalService studentPrincipalService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenByUser(request);
        if (token != null && jWTProvider.validateToken(token)) {
            String username = jWTProvider.getUsernameFromToken(token);
            StudentPrincipal studentPrincipal = (StudentPrincipal) studentPrincipalService.loadUserByUsername(username);
            Authentication authentication = new UsernamePasswordAuthenticationToken(studentPrincipal, null, studentPrincipal.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String getTokenByUser(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}
