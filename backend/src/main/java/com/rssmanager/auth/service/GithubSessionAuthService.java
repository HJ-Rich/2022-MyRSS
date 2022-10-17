package com.rssmanager.auth.service;

import com.rssmanager.auth.controller.dto.CertificateResponse;
import com.rssmanager.auth.controller.dto.LoginRequest;
import com.rssmanager.auth.controller.dto.LoginResponse;
import com.rssmanager.auth.service.dto.GithubAccessTokenResponse;
import com.rssmanager.auth.service.dto.GithubUserInfoResponse;
import com.rssmanager.config.GithubOAuthConfig;
import com.rssmanager.member.controller.dto.MemberResponse;
import com.rssmanager.member.domain.Member;
import com.rssmanager.member.service.MemberService;
import com.rssmanager.util.SessionManager;
import java.util.Map;
import java.util.Objects;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class GithubSessionAuthService implements AuthService {
    private final MemberService memberService;
    private final SessionManager sessionManager;
    private final GithubOAuthConfig githubOAuthConfig;

    public GithubSessionAuthService(final MemberService memberService, final SessionManager sessionManager,
                                    final GithubOAuthConfig githubOAuthConfig) {
        this.memberService = memberService;
        this.sessionManager = sessionManager;
        this.githubOAuthConfig = githubOAuthConfig;
    }

    @Override
    public LoginResponse login(final LoginRequest loginRequest) {
        final var code = loginRequest.getCode();

        final var accessToken = exchangeCodeToAccessToken(code);
        if (Objects.isNull(accessToken)) {
            throw new RuntimeException("");
        }

        final var githubUserInfoResponse = fetchUserInfoUsingAccessToken(accessToken);
        final var member = createMemberByResponseUserInfo(githubUserInfoResponse);
        final var savedMember = memberService.save(member);
        final var httpSession = sessionManager.login(savedMember);

        return new LoginResponse(true);
    }

    @Override
    public CertificateResponse certificate() {
        final var member = sessionManager.<Member>getAttribute("member");

        if (Objects.isNull(member)) {
            return CertificateResponse.from(false);
        }

        return CertificateResponse.from(MemberResponse.from(member));
    }

    private String exchangeCodeToAccessToken(final String code) {
        return WebClient.create()
                .post()
                .uri(githubOAuthConfig.getAccessTokenUrl())
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(githubOAuthConfig.createAccessTokenRequest(code)), Map.class)
                .retrieve()
                .bodyToMono(GithubAccessTokenResponse.class)
                .block()
                .getAccessToken();
    }

    private GithubUserInfoResponse fetchUserInfoUsingAccessToken(final String accessToken) {
        return WebClient.create()
                .get()
                .uri(githubOAuthConfig.getUserInfoUrl())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(GithubUserInfoResponse.class)
                .block();
    }

    private Member createMemberByResponseUserInfo(final GithubUserInfoResponse githubUserInfoResponse) {
        return Member.builder()
                .providerId(githubUserInfoResponse.getProviderId())
                .name(githubUserInfoResponse.getName())
                .imageUrl(githubUserInfoResponse.getImageUrl())
                .build();
    }
}
