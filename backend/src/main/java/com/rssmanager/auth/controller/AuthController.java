package com.rssmanager.auth.controller;

import com.rssmanager.auth.controller.dto.CertificateResponse;
import com.rssmanager.auth.controller.dto.LoginRequest;
import com.rssmanager.auth.controller.dto.LoginResponse;
import com.rssmanager.auth.service.AuthService;
import com.rssmanager.util.SessionManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth")
@RestController
public class AuthController {

    private final AuthService authService;
    private final SessionManager sessionManager;

    public AuthController(final AuthService authService, final SessionManager sessionManager) {
        this.authService = authService;
        this.sessionManager = sessionManager;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = authService.login(loginRequest);

        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/certificate")
    public ResponseEntity<CertificateResponse> certificate() {
        CertificateResponse certificateResponse = authService.certificate();
        return ResponseEntity.ok(certificateResponse);
    }

    @PostMapping("/invalidate")
    public ResponseEntity<Void> invalidate() {
        sessionManager.invalidate();

        return ResponseEntity.ok().build();
    }
}
