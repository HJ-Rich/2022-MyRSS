package com.rssmanager.rss.repository;

import com.rssmanager.rss.domain.Feed;
import java.util.List;
import org.springframework.data.repository.Repository;

public interface FeedRepository extends Repository<Feed, Long> {

    List<Feed> findAll();

    List<Feed> findByRssId(Long rssId);

    Feed save(Feed feed);

    boolean existsByLink(String link);
}
