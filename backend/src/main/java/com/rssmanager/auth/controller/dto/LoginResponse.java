package com.rssmanager.auth.controller.dto;

import lombok.Getter;

@Getter
public class LoginResponse {

    private final String cookie;

    public LoginResponse(final String cookie) {
        this.cookie = cookie;
    }
}
