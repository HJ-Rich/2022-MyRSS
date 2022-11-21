package com.rssmanager.auth.application;

import com.rssmanager.auth.application.dto.CertificateResponse;
import com.rssmanager.auth.application.dto.LoginRequest;
import com.rssmanager.auth.application.dto.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);

    CertificateResponse certificate();
}
