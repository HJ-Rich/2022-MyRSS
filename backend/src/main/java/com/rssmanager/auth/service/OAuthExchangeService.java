package com.rssmanager.auth.service;

import com.rssmanager.auth.controller.dto.LoginRequest;
import com.rssmanager.member.domain.Member;

@FunctionalInterface
public interface OAuthExchangeService {
    Member fetchMember(LoginRequest loginRequest);
}
