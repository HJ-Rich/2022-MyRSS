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

    @Override
    public Member findById(final Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("존재하지 않는 회원 : {}", id)));
    }

    @Transactional
    @Override
    public Member save(final Member member) {
        final var providerId = member.getProviderId();
        final var byProviderId = memberRepository.findByProviderId(providerId);

        if (byProviderId.isPresent()) {
            return memberRepository.findByProviderId(providerId)
                    .orElseThrow(IllegalArgumentException::new);
        }

        return memberRepository.save(member);
    }
}
