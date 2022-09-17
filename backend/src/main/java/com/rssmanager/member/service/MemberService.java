package com.rssmanager.member.service;

import com.rssmanager.member.controller.dto.MemberResponse;
import com.rssmanager.member.domain.Member;

public interface MemberService {

    MemberResponse findById(Long id);

    MemberResponse save(Member member);
}
