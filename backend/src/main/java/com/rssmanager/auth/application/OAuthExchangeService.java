package com.rssmanager.auth.application;

import com.rssmanager.auth.application.dto.LoginRequest;
import com.rssmanager.member.domain.Member;

@FunctionalInterface
public interface OAuthExchangeService {
    Member fetchMember(LoginRequest loginRequest);
}
