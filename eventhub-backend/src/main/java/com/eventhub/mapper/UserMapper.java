package com.eventhub.mapper;

import org.springframework.stereotype.Component;

import com.eventhub.dto.UserRegistrationRequest;
import com.eventhub.dto.UserRequest;
import com.eventhub.dto.UserResponse;
import com.eventhub.entity.User;
import com.eventhub.enums.Role;

@Component
public class UserMapper {

    // Registration DTO to Entity
    public User toEntity(UserRegistrationRequest request) {

        User user = new User();

        user.setFullName(request.getFullName());

        user.setEmail(request.getEmail());

        user.setMobile(request.getMobile());

        user.setRole(
            request.getRole() != null
                ? request.getRole()
                : Role.USER
        );

        return user;
    }

    // Entity to Response DTO
    public UserResponse toResponse(User user) {

        if (user == null) {
            return null;
        }

        UserResponse response = new UserResponse();

        response.setId(user.getId());

        response.setFullName(user.getFullName());

        response.setEmail(user.getEmail());

        response.setMobile(user.getMobile());

        response.setRole(user.getRole());

        return response;
    }

    // Update User Request to Entity
    public void updateEntity(
            UserRequest request,
            User user) {

        user.setFullName(request.getFullName());

        user.setMobile(request.getMobile());
    }
}