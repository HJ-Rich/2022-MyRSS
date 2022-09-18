package com.rssmanager.member.service;

import com.rssmanager.member.controller.dto.MemberResponse;
import com.rssmanager.member.domain.Member;
import com.rssmanager.member.repository.MemberRepository;
import java.util.Optional;
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
    public MemberResponse findById(final Long id) {
        Member foundMember = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("존재하지 않는 회원 : {}", id)));

        return MemberResponse.from(foundMember);
    }

    @Transactional
    @Override
    public MemberResponse save(final Member member) {
        Long providerId = member.getProviderId();
        Optional<Member> byProviderId = memberRepository.findByProviderId(providerId);

        if (byProviderId.isPresent()) {
            Member foundMember = memberRepository.findByProviderId(providerId)
                    .orElseThrow(IllegalArgumentException::new);
            return MemberResponse.from(foundMember);
        }

        Member savedMember = memberRepository.save(member);
        return MemberResponse.from(savedMember);
    }
}
