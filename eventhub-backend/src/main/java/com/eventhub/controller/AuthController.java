package com.eventhub.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.eventhub.dto.LoginRequest;
import com.eventhub.dto.LoginResponse;
import com.eventhub.dto.UserRegistrationRequest;
import com.eventhub.dto.UserResponse;
import com.eventhub.service.AuthService;
import com.eventhub.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    public AuthController(
            AuthService authService,
            UserService userService) {

        this.authService = authService;
        this.userService = userService;
    }


    // REGISTER
   

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
            @Valid @RequestBody UserRegistrationRequest request) {

        UserResponse response =
                userService.registerUser(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

 
    // LOGIN
 

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        LoginResponse response =
                authService.login(request);

        return ResponseEntity.ok(response);
    }
}