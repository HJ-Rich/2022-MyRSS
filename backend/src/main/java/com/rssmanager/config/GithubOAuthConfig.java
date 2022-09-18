package com.rssmanager.config;

import java.util.Map;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConstructorBinding
@ConfigurationProperties(prefix = "oauth.github")
public class GithubOAuthConfig {

    private final String clientId;
    private final String clientSecret;
    private final String accessTokenUrl;
    private final String userInfoUrl;

    public GithubOAuthConfig(final String clientId, final String clientSecret,
                             final String accessTokenUrl, final String userInfoUrl) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.accessTokenUrl = accessTokenUrl;
        this.userInfoUrl = userInfoUrl;
    }

    public Map<String, String> createAccessTokenRequest(String code) {
        return Map.of(
                "client_id", clientId,
                "client_secret", clientSecret,
                "code", code
        );
    }
}
