package com.rssmanager.auth.controller.dto;

import lombok.Getter;

@Getter
public class LoginRequest {

    private final String provider;
    private final String code;

    public LoginRequest(final String provider, final String code) {
        this.provider = provider;
        this.code = code;
    }
}
