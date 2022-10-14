package com.rssmanager.rss.repository;

import com.rssmanager.rss.domain.Subscribe;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface SubscribeRepository extends Repository<Subscribe, Long> {
    Subscribe save(Subscribe subscribe);

    @Query("select s.id from Subscribe s where s.rss.id = :rssId and s.member.id = :memberId")
    Optional<Long> findIdByRssIdAndMemberId(@Param("rssId") Long rssId, @Param("memberId") Long memberId);
}
