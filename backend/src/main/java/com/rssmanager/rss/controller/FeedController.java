package com.rssmanager.rss.controller;

import com.rssmanager.rss.controller.dto.FeedResponses;
import com.rssmanager.rss.service.FeedService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/feeds")
@RestController
public class FeedController {

    private final FeedService feedService;

    public FeedController(final FeedService feedService) {
        this.feedService = feedService;
    }

    @GetMapping
    public ResponseEntity<FeedResponses> findFeeds(Pageable pageable) {
        final FeedResponses feeds = feedService.findFeeds(pageable);

        return ResponseEntity.ok(feeds);
    }
}
