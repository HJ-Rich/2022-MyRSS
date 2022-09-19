package com.rssmanager.config;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableAsync
@EnableScheduling
@ConfigurationPropertiesScan
@EnableRedisHttpSession
@Configuration
public class Configurations {
}
