package com.rssmanager.member.service;

import com.rssmanager.member.domain.Member;
import com.rssmanager.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class JpaMemberService implements MemberService {
    private final MemberRepository memberRepository;

    public JpaMemberService(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    @Override
    public Member save(final Member member) {
        return memberRepository.findByProviderId(member.getProviderId())
                .orElseGet(() -> memberRepository.save(member));
    }
}
