package com.rssmanager.auth.service;

import com.rssmanager.auth.controller.dto.LoginRequest;
import com.rssmanager.auth.controller.dto.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest loginRequest);
}