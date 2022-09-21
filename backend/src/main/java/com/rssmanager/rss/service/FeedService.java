package com.rssmanager.rss.service;

import com.rssmanager.rss.domain.Feed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FeedService {

    Page<Feed> findFeeds(Pageable pageable);
}
