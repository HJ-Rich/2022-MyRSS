package com.rssmanager.rss.service;

import com.rssmanager.rss.controller.dto.FeedResponses;
import com.rssmanager.rss.domain.Feed;
import org.springframework.data.domain.Pageable;

public interface FeedService {

    FeedResponses findFeeds(Pageable pageable);

    Feed findById(Long id);
}
