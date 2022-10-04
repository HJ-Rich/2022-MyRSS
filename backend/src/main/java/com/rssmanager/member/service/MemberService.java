package com.rssmanager.member.service;

import com.rssmanager.member.domain.Member;

public interface MemberService {

    Member findById(Long id);

    Member save(Member member);
}
