package com.rssmanager.rss.repository;

import com.rssmanager.rss.domain.Bookmark;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

public interface BookmarkRepository extends Repository<Bookmark, Long> {
    Bookmark save(Bookmark bookmark);

    Page<Bookmark> findAllByMemberId(Long memberId, Pageable pageable);

    Optional<Bookmark> findByMemberIdAndFeedId(Long memberId, Long feedId);

    boolean existsByMemberIdAndFeedId(Long memberId, Long feedId);
}
