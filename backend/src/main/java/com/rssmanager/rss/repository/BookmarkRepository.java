package com.rssmanager.rss.repository;

import com.rssmanager.rss.domain.Bookmark;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface BookmarkRepository extends Repository<Bookmark, Long> {
    Bookmark save(Bookmark bookmark);

    Slice<Bookmark> findAllByMemberIdOrderByFeed_UpdateDateDesc(Long memberId, Pageable pageable);

    @Query("select b from Bookmark b join fetch b.member join fetch b.feed where b.member.id = :memberId and b.feed.id = :feedId")
    Optional<Bookmark> findFirstByMemberIdAndFeedId(@Param("memberId") Long memberId, @Param("feedId") Long feedId);

    void deleteById(Long id);
}
