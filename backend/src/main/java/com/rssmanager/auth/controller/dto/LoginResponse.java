package com.rssmanager.auth.controller.dto;

import com.rssmanager.member.controller.dto.MemberResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponse {

    private final MemberResponse memberResponse;

    @Builder
    public LoginResponse(final MemberResponse memberResponse) {
        this.memberResponse = memberResponse;
    }
}
