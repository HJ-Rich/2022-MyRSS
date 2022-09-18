package com.rssmanager.config;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@ConfigurationPropertiesScan
@EnableRedisHttpSession
@Configuration
public class Configurations {
}
