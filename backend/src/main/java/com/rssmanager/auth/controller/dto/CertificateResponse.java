package com.rssmanager.auth.controller.dto;

import lombok.Getter;

@Getter
public class CertificateResponse {

    private final boolean loggedIn;

    public CertificateResponse(final boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public static CertificateResponse from(boolean loggedIn) {
        return new CertificateResponse(loggedIn);
    }
}
