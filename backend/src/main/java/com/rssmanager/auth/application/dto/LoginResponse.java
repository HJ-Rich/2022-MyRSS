package com.rssmanager.auth.application.dto;

import lombok.Getter;

@Getter
public class LoginResponse {
    private final boolean success;

    public LoginResponse(final boolean success) {
        this.success = success;
    }
}
