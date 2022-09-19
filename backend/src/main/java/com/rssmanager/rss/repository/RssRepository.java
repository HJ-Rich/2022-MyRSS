package com.rssmanager.rss.repository;

import com.rssmanager.rss.domain.Feed;
import com.rssmanager.rss.domain.Rss;
import java.util.List;
import org.springframework.data.repository.Repository;

public interface RssRepository extends Repository<Rss, Long> {

    Rss save(Rss rss);

    List<Rss> findAll();

    void deleteById(Long rssId);

    void saveAll(Iterable<Feed> newFeedsToSave);
}
