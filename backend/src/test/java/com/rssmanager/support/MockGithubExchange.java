package com.rssmanager.support;

import com.rssmanager.auth.service.OAuthExchangeService;
import com.rssmanager.member.domain.Member;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class MockGithubExchange {
    private static final Member RICHARD = new Member(1L, 1L, "Richard", "richard.png");

    @Bean
    public OAuthExchangeService oAuthExchangeService() {
        return loginRequest -> RICHARD;
    }
}
