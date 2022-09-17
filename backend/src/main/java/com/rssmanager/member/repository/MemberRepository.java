package com.rssmanager.member.repository;

import com.rssmanager.member.domain.Member;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface MemberRepository extends Repository<Member, Long> {
    
    Optional<Member> findById(Long id);

    Member save(Member member);
}
