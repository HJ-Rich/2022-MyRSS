package com.rssmanager.rss.repository;

import com.rssmanager.rss.domain.Feed;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

public interface FeedRepository extends Repository<Feed, Long> {

    List<Feed> findAll();

    Page<Feed> findAll(Pageable pageable);

    List<Feed> findByRssId(Long rssId);

    Optional<Feed> findById(Long feedId);

    Feed save(Feed feed);

    boolean existsByLink(String link);

    void saveAll(Iterable<Feed> newFeedsToSave);
}
