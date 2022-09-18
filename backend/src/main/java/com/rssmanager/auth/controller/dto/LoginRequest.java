package com.rssmanager.auth.controller.dto;

import lombok.Getter;

@Getter
public class LoginRequest {

    private String code;

    private LoginRequest() {
    }

    public LoginRequest(final String code) {
        this.code = code;
    }
}
