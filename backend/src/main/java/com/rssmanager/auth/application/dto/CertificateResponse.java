package com.rssmanager.auth.application.dto;

import com.rssmanager.member.controller.dto.MemberResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CertificateResponse {
    private boolean loggedIn;
    private MemberResponse member;

    protected CertificateResponse() {
    }

    @Builder
    public CertificateResponse(final boolean loggedIn, final MemberResponse member) {
        this.loggedIn = loggedIn;
        this.member = member;
    }

    public static CertificateResponse from(final boolean loggedIn, final MemberResponse memberResponse) {
        return new CertificateResponse(loggedIn, memberResponse);
    }
}
