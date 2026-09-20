package com.eventhub.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventhub.dto.UserRegistrationRequest;
import com.eventhub.dto.UserRequest;
import com.eventhub.dto.UserResponse;
import com.eventhub.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    // CONSTRUCTOR
   
    public UserController(
            UserService userService) {

        this.userService = userService;
    }

    // REGISTER
    // POST /api/users/register
    // PUBLIC
   

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(
            @Valid @RequestBody UserRegistrationRequest request) {

        UserResponse response =
                userService.registerUser(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // CURRENT USER
    // GET /api/users/me
    // LOGIN REQUIRED
      @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser() {

        UserResponse response =
                userService.getCurrentUser();

        return ResponseEntity.ok(response);
    }

    // UPDATE CURRENT USER
    // PUT /api/users/me
    // LOGIN REQUIRED
    
    @PutMapping("/me")
    public ResponseEntity<UserResponse> updateUser(
            @Valid @RequestBody UserRequest request) {

        UserResponse response =
                userService.updateUser(request);

        return ResponseEntity.ok(response);
    }

  
    // GET USER BY ID
    // ADMIN ONLY


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> getUserById(
            @PathVariable Long id) {

        UserResponse response =
                userService.getUserById(id);

        return ResponseEntity.ok(response);
    }

 
    // GET ALL USERS
    // ADMIN ONLY
   

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> getAllUsers() {

        List<UserResponse> users =
                userService.getAllUsers();

        return ResponseEntity.ok(users);
    }

    
    // DELETE USER
    // ADMIN ONLY


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id) {

        userService.deleteUser(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}