package com.rssmanager.rss.repository;

import com.rssmanager.rss.domain.Feed;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface FeedRepository extends Repository<Feed, Long> {
    Optional<Feed> findById(Long feedId);

    boolean existsByLink(String link);

    void saveAll(Iterable<Feed> newFeedsToSave);
}
