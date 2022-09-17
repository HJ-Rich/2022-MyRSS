package com.rssmanager.auth.service;

import com.rssmanager.auth.controller.dto.LoginRequest;
import com.rssmanager.auth.controller.dto.LoginResponse;
import com.rssmanager.member.service.MemberService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class GithubSessionService implements AuthService {

    private final MemberService memberService;
    private final RedisTemplate<String, String> redisTemplate;

    public GithubSessionService(final MemberService memberService, final RedisTemplate<String, String> redisTemplate) {
        this.memberService = memberService;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public LoginResponse login(final LoginRequest loginRequest) {
        return null;
    }
}
