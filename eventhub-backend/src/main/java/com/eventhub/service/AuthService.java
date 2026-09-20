package com.eventhub.service;

import com.eventhub.dto.LoginRequest;
import com.eventhub.dto.LoginResponse;

public interface AuthService {

    // Login user
    LoginResponse login(LoginRequest request);
}