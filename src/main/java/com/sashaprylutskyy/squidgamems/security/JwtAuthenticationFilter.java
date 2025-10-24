package com.sashaprylutskyy.squidgamems.security;

import com.sashaprylutskyy.squidgamems.model.User;
import io.jsonwebtoken.Claims; // Потрібен цей імпорт
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority; // Потрібен цей імпорт
import org.springframework.security.core.authority.SimpleGrantedAuthority; // Потрібен цей імпорт
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List; // Потрібен цей імпорт

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String token;
        final String subject; // email

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        token = authHeader.substring(7);

        try {
            subject = jwtService.extractSubject(token);
        } catch (Exception e) {
            filterChain.doFilter(request, response);
            return;
        }

        // !!! ЛОГІКУ ЗМІНЕНО !!!
        if (subject != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (jwtService.isTokenValid(token, subject)) {
                Claims claims = jwtService.extractAllClaims(token);

                UserDetails userPrincipal = new User(
                        claims.get("userId", Long.class),
                        subject
                );

                String roleFromClaim = claims.get("role", String.class);
                List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(roleFromClaim));

                UsernamePasswordAuthenticationToken authToken = new
                        UsernamePasswordAuthenticationToken(userPrincipal, null, authorities);

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}