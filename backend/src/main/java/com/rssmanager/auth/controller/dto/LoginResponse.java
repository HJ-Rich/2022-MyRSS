package com.rssmanager.auth.controller.dto;

import lombok.Getter;

@Getter
public class LoginResponse {

    private final boolean firstLogin;

    public LoginResponse(final boolean firstLogin) {
        this.firstLogin = firstLogin;
    }
}
