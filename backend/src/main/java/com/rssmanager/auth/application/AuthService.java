package com.rssmanager.auth.application;

import com.rssmanager.auth.controller.dto.CertificateResponse;
import com.rssmanager.auth.controller.dto.LoginRequest;
import com.rssmanager.auth.controller.dto.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);

    CertificateResponse certificate();
}
