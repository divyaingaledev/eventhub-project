package com.eventhub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.eventhub.security.JwtAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter) {

        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
            // Disable CSRF because this is a JWT REST API
            .csrf(csrf -> csrf.disable())

            // Enable CORS
            .cors(cors -> {})

            // No HTTP session
            .sessionManagement(session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            )

            .authorizeHttpRequests(auth -> auth

                // CORS preflight
                .requestMatchers(HttpMethod.OPTIONS, "/**")
                .permitAll()

                // Login and registration
                .requestMatchers("/api/auth/**")
                .permitAll()

                // Public event APIs
                .requestMatchers(
                    "/api/events/**",
                    "/api/categories/**",
                    "/api/venues/**"
                )
                .permitAll()

                // Booking requires login
                .requestMatchers("/api/bookings/**")
                .authenticated()

                // User management only for ADMIN
                .requestMatchers("/api/users/**")
                .hasRole("ADMIN")

                // Everything else requires login
                .anyRequest()
                .authenticated()
            )

            // JWT filter
            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}