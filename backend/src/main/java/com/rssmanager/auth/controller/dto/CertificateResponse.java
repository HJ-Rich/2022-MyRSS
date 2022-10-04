package com.rssmanager.auth.controller.dto;

import com.rssmanager.member.controller.dto.MemberResponse;
import lombok.Getter;

@Getter
public class CertificateResponse {

    private final boolean loggedIn;
    private final MemberResponse member;

    public CertificateResponse(final boolean loggedIn, final MemberResponse member) {
        this.loggedIn = loggedIn;
        this.member = member;
    }

    public CertificateResponse(final boolean loggedIn) {
        this(loggedIn, null);
    }

    public static CertificateResponse from(boolean loggedIn) {
        return new CertificateResponse(loggedIn);
    }

    public static CertificateResponse from(MemberResponse member) {
        return new CertificateResponse(true, member);
    }
}
