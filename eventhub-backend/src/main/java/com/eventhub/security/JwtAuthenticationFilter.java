package com.eventhub.security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authorizationHeader =
                request.getHeader("Authorization");

        System.out.println();
        System.out.println("JWT FILTER ");
        System.out.println(
                "Request: "
                + request.getMethod()
                + " "
                + request.getRequestURI()
        );

        // No Authorization header
        if (authorizationHeader == null ||
                !authorizationHeader.startsWith("Bearer ")) {

            System.out.println(
                    "Authorization header: NOT FOUND"
            );

            filterChain.doFilter(request, response);
            return;
        }

        System.out.println(
                "Authorization header: FOUND"
        );

        String token =
                authorizationHeader.substring(7).trim();

        try {

            // Validate token
            if (!jwtService.isTokenValid(token)) {

                System.out.println(
                        "JWT: INVALID"
                );

                SecurityContextHolder.clearContext();

                filterChain.doFilter(request, response);
                return;
            }

            // Extract email
            String email =
                    jwtService.extractEmail(token);

            // Extract role
            String role =
                    jwtService.extractRole(token);

            // Default role
            if (role == null ||
                    role.trim().isEmpty()) {

                role = "USER";

            } else {

                role = role.trim().toUpperCase();

                // Prevent ROLE_ROLE_USER
                if (role.startsWith("ROLE_")) {
                    role = role.substring(5);
                }
            }

            String authority = "ROLE_" + role;

            System.out.println(
                    "JWT: VALID"
            );

            System.out.println(
                    "Email: " + email
            );

            System.out.println(
                    "Role: " + role
            );

            System.out.println(
                    "Authority: " + authority
            );

            // Create authentication
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            email,
                            null,
                            List.of(
                                new SimpleGrantedAuthority(
                                    authority
                                )
                            )
                    );

            // Set authentication
            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authentication);

            System.out.println(
                    "SecurityContext: AUTHENTICATED"
            );

        } catch (Exception e) {

            System.out.println(
                    "JWT ERROR: " + e.getMessage()
            );

            SecurityContextHolder.clearContext();
        }

        System.out.println(
                "================================"
        );

        filterChain.doFilter(request, response);
    }
}