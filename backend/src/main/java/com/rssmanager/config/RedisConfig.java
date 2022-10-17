package com.rssmanager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {
    @Value("${spring.redis.host}")
    public String host;

    @Value("${spring.redis.port}")
    public int port;

    @Value("${spring.redis.password}")
    public String password;


    @Bean
    public RedisTemplate<String, String> redisTemplate(final RedisConnectionFactory connectionFactory) {
        final var redisTemplate = new RedisTemplate<String, String>();
        redisTemplate.setConnectionFactory(connectionFactory);

        return redisTemplate;
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        final var configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(this.host);
        configuration.setPort(this.port);
        configuration.setPassword(this.password);

        return new LettuceConnectionFactory(configuration);
    }
}
