package com.rssmanager.auth.application;

import com.rssmanager.auth.application.dto.GithubAccessTokenResponse;
import com.rssmanager.auth.application.dto.GithubUserInfoResponse;
import com.rssmanager.auth.controller.dto.LoginRequest;
import com.rssmanager.config.GithubOAuthConfig;
import com.rssmanager.member.domain.Member;
import java.util.Map;
import java.util.Objects;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class GithubOAuthExchangeService implements OAuthExchangeService {
    private final GithubOAuthConfig githubOAuthConfig;

    public GithubOAuthExchangeService(final GithubOAuthConfig githubOAuthConfig) {
        this.githubOAuthConfig = githubOAuthConfig;
    }

    @Override
    public Member fetchMember(final LoginRequest loginRequest) {
        final var code = loginRequest.getCode();

        final var accessToken = exchangeCodeToAccessToken(code);
        if (Objects.isNull(accessToken)) {
            throw new RuntimeException("");
        }

        final var githubUserInfoResponse = fetchUserInfoUsingAccessToken(accessToken);
        return createMemberByResponseUserInfo(githubUserInfoResponse);
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
