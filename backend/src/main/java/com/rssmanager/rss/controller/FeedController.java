package com.rssmanager.rss.controller;

import com.rssmanager.rss.controller.dto.FeedResponse;
import com.rssmanager.rss.controller.dto.FeedResponses;
import com.rssmanager.rss.service.FeedService;
import java.util.List;
import java.util.stream.Collectors;
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
        final List<FeedResponse> feedResponse = feedService.findFeeds(pageable)
                .getContent()
                .stream()
                .map(FeedResponse::from)
                .collect(Collectors.toList());
        final FeedResponses feedResponses = FeedResponses.from(feedResponse);

        return ResponseEntity.ok(feedResponses);
    }
}
