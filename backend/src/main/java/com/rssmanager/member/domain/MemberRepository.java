package com.rssmanager.member.domain;

import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface MemberRepository extends Repository<Member, Long> {
    Member save(Member member);

    Optional<Member> findByProviderId(Long providerId);
}
