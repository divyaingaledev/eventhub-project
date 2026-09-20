
package com.eventhub.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eventhub.dto.UserRegistrationRequest;
import com.eventhub.dto.UserRequest;
import com.eventhub.dto.UserResponse;
import com.eventhub.entity.User;
import com.eventhub.enums.Role;
import com.eventhub.exception.ResourceNotFoundException;
import com.eventhub.mapper.UserMapper;
import com.eventhub.repository.UserRepository;
import com.eventhub.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    // CONSTRUCTOR
    
    public UserServiceImpl(
            UserRepository userRepository,
            UserMapper userMapper,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

   
    // REGISTER USER
    
    @Override
    public UserResponse registerUser(
            UserRegistrationRequest request) {

        // Check duplicate email
        if (userRepository.existsByEmail(
                request.getEmail())) {

            throw new IllegalArgumentException(
                    "Email already registered"
            );
        }

        // Convert request to entity
        User user =
                userMapper.toEntity(request);

        // Encode password
        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );

        // Default role
        if (user.getRole() == null) {

            user.setRole(
                    Role.USER
            );
        }

        // Save user
        User savedUser =
                userRepository.save(user);

        return userMapper.toResponse(
                savedUser
        );
    }

   
    // GET USER BY ID
    
    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(
            Long id) {

        User user =
                userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id: "
                                        + id
                        )
                );

        return userMapper.toResponse(
                user
        );
    }

    
    // GET ALL USERS
    

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }

   
    // GET CURRENT USER
    

    @Override
    @Transactional(readOnly = true)
    public UserResponse getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated()) {

            throw new IllegalStateException(
                    "User is not authenticated"
            );
        }

        String email =
                authentication.getName();

        User user =
                userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Authenticated user not found"
                        )
                );

        return userMapper.toResponse(
                user
        );
    }

    // UPDATE CURRENT USER
  
    @Override
    public UserResponse updateUser(
            UserRequest request) {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated()) {

            throw new IllegalStateException(
                    "User is not authenticated"
            );
        }

        String currentEmail =
                authentication.getName();

        User user =
                userRepository.findByEmail(
                        currentEmail
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Authenticated user not found"
                        )
                );

        // Update allowed user fields
        userMapper.updateEntity(
                request,
                user
        );

        // Save
        User updatedUser =
                userRepository.save(user);

        return userMapper.toResponse(
                updatedUser
        );
    }

    
    // DELETE USER
    
    @Override
    public void deleteUser(Long id) {

        User user =
                userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id: "
                                        + id
                        )
                );

        userRepository.delete(user);
    }
}

