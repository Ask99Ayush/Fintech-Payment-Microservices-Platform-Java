package com.fintech.api_gateway__services.filter;

import com.fintech.api_gateway__services.security.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    // Public APIs that don't require JWT
    private static final String[] PUBLIC_PATHS = {
            "/api/auth/login",
            "/api/auth/signup"
    };

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        log.info("Incoming request path: {}", path);

        // Allow public endpoints
        if (path.contains("/api/auth")) {
            chain.doFilter(request, response);
            return;
        }

        // Check Authorization header
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\": \"Missing token\"}");
            return;
        }

        String token = header.substring(7);

        // Validate token
        if (!jwtUtil.isTokenValid(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\": \"Invalid token\"}");
            return;
        }

        log.info("✅ Valid token for: {}", jwtUtil.extractEmail(token));

        chain.doFilter(request, response);
    }
}