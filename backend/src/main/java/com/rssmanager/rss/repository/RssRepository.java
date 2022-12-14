package com.rssmanager.rss.repository;

import com.rssmanager.rss.domain.Feed;
import com.rssmanager.rss.domain.Rss;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface RssRepository extends Repository<Rss, Long> {
    Rss save(Rss rss);

    Optional<Rss> findByRssUrl(String rssUrl);

    List<Rss> findAll();

    void saveAll(Iterable<Feed> newFeedsToSave);
}
