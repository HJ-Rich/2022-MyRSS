package com.rssmanager.auth.controller;

import com.rssmanager.auth.application.AuthService;
import com.rssmanager.auth.application.dto.CertificateResponse;
import com.rssmanager.auth.application.dto.LoginRequest;
import com.rssmanager.auth.application.dto.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth")
@RestController
public class AuthController {
    private final AuthService authService;

    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody final LoginRequest loginRequest) {
        final var loginResponse = authService.login(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/certificate")
    public ResponseEntity<CertificateResponse> certificate() {
        final var certificateResponse = authService.certificate();
        return ResponseEntity.ok(certificateResponse);
    }

    @PostMapping("/invalidate")
    public ResponseEntity<Void> invalidate() {
        authService.invalidate();
        return ResponseEntity.ok().build();
    }
}
