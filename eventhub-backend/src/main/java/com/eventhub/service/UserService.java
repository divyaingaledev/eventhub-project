package com.eventhub.service;

import java.util.List;

import com.eventhub.dto.UserRegistrationRequest;
import com.eventhub.dto.UserRequest;
import com.eventhub.dto.UserResponse;

public interface UserService {

    // Register new user
    UserResponse registerUser(
            UserRegistrationRequest request
    );

    // Get user by ID
    UserResponse getUserById(Long id);

    // Get current logged-in user
    UserResponse getCurrentUser();

    // Update current logged-in user
    UserResponse updateUser(
            UserRequest request
    );

    // Get all users
    List<UserResponse> getAllUsers();

    // Delete user
    void deleteUser(Long id);
}