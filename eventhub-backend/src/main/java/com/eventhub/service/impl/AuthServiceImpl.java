package com.eventhub.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eventhub.dto.LoginRequest;
import com.eventhub.dto.LoginResponse;
import com.eventhub.entity.User;
import com.eventhub.repository.UserRepository;
import com.eventhub.security.JwtService;
import com.eventhub.service.AuthService;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {

        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Invalid email or password"
                        )
                );

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new IllegalArgumentException(
                    "Invalid email or password"
            );
        }

        String role = user.getRole().name();

        System.out.println(
                "========== LOGIN DEBUG =========="
        );

        System.out.println(
                "User ID: " + user.getId()
        );

        System.out.println(
                "Email: " + user.getEmail()
        );

        System.out.println(
                "Role from database: " + role
        );

        String token = jwtService.generateToken(
                user.getId(),
                user.getEmail(),
                role
        );

        System.out.println(
                "JWT generated successfully"
        );

        System.out.println(
                "================================="
        );

        return new LoginResponse(
                token,
                "Bearer",
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                role
        );
    }
}