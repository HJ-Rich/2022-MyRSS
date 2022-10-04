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
import java.util.Base64;
import java.util.Map;
import java.util.Objects;
import javax.servlet.http.HttpSession;
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
    public LoginResponse login(LoginRequest loginRequest) {
        String code = loginRequest.getCode();
        WebClient webClient = WebClient.create();

        String accessToken = exchangeCodeToAccessToken(code, webClient);
        GithubUserInfoResponse githubUserInfoResponse = fetchUserInfoUsingAccessToken(webClient, accessToken);
        Member member = createMemberByResponseUserInfo(githubUserInfoResponse);
        MemberResponse memberResponse = memberService.save(member);
        HttpSession httpSession = sessionManager.login(memberResponse.getId());

        return new LoginResponse(new String(Base64.getEncoder().encode(httpSession.getId().getBytes())));
    }

    @Override
    public CertificateResponse certificate() {
        Long memberId = sessionManager.getAttribute("memberId");

        if (Objects.isNull(memberId)) {
            return CertificateResponse.from(false);
        }

        MemberResponse member = memberService.findById(memberId);
        return CertificateResponse.from(member);
    }

    private String exchangeCodeToAccessToken(String code, WebClient webClient) {
        return webClient.post()
                .uri(githubOAuthConfig.getAccessTokenUrl())
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(githubOAuthConfig.createAccessTokenRequest(code)), Map.class)
                .retrieve()
                .bodyToMono(GithubAccessTokenResponse.class)
                .block()
                .getAccessToken();
    }

    private GithubUserInfoResponse fetchUserInfoUsingAccessToken(final WebClient webClient, final String accessToken) {
        return webClient.get()
                .uri(githubOAuthConfig.getUserInfoUrl())
                .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", accessToken))
                .retrieve()
                .bodyToMono(GithubUserInfoResponse.class)
                .block();
    }

    private Member createMemberByResponseUserInfo(GithubUserInfoResponse githubUserInfoResponse) {
        return Member.builder()
                .providerId(githubUserInfoResponse.getProviderId())
                .name(githubUserInfoResponse.getName())
                .imageUrl(githubUserInfoResponse.getImageUrl())
                .build();
    }
}
