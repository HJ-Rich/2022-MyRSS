package com.rssmanager.config;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import redis.embedded.RedisServer;

@Profile({"test"})
@Component
public class EmbeddedRedisConfig {
    private final RedisServer redisServer;

    public EmbeddedRedisConfig(final RedisConfig redisConfig) {
        this.redisServer = RedisServer.builder()
                .port(redisConfig.getPort())
                .setting("maxmemory 32MB")
                .build();
    }

    @PostConstruct
    public void start() {
        redisServer.start();
    }

    @PreDestroy
    public void stop() {
        redisServer.stop();
    }
}
