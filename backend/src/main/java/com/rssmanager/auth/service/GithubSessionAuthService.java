package com.rssmanager.auth.service;

import com.rssmanager.auth.controller.dto.CertificateResponse;
import com.rssmanager.auth.controller.dto.LoginRequest;
import com.rssmanager.auth.controller.dto.LoginResponse;
import com.rssmanager.member.application.MemberService;
import com.rssmanager.member.controller.dto.MemberResponse;
import com.rssmanager.util.SessionManager;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public class GithubSessionAuthService implements AuthService {
    private final MemberService memberService;
    private final SessionManager sessionManager;
    private final OAuthExchangeService oAuthExchangeService;

    public GithubSessionAuthService(final MemberService memberService, final SessionManager sessionManager,
                                    final OAuthExchangeService oAuthExchangeService) {
        this.memberService = memberService;
        this.sessionManager = sessionManager;
        this.oAuthExchangeService = oAuthExchangeService;
    }

    @Override
    public LoginResponse login(final LoginRequest loginRequest) {
        final var member = oAuthExchangeService.fetchMember(loginRequest);
        final var savedMember = memberService.save(member);
        sessionManager.login(savedMember);

        return new LoginResponse(true);
    }

    @Override
    public CertificateResponse certificate() {
        final var member = sessionManager.getLoginMember();

        return CertificateResponse.from(
                Objects.nonNull(member),
                MemberResponse.from(member)
        );
    }
}
