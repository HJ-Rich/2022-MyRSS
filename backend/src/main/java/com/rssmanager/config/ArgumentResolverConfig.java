package com.rssmanager.config;

import com.rssmanager.auth.support.LoginMemberResolver;
import com.rssmanager.util.SessionManager;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ArgumentResolverConfig implements WebMvcConfigurer {
    private final SessionManager sessionManager;

    public ArgumentResolverConfig(final SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(loginMemberArgumentResolver());
    }

    @Bean
    public LoginMemberResolver loginMemberArgumentResolver() {
        return new LoginMemberResolver(sessionManager);
    }
}

