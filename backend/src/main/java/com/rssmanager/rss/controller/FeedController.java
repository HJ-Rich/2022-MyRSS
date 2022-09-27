package com.rssmanager.rss.controller;

import com.rssmanager.rss.controller.dto.FeedResponse;
import com.rssmanager.rss.controller.dto.FeedResponses;
import com.rssmanager.rss.domain.Feed;
import com.rssmanager.rss.service.FeedService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/feeds")
@RestController
public class FeedController {

    private final FeedService feedService;

    public FeedController(final FeedService feedService) {
        this.feedService = feedService;
    }

    @GetMapping
    public ResponseEntity<FeedResponses> findFeeds(Pageable pageable,
                                                   @RequestParam(required = false) boolean subscribed) {
        Page<Feed> feeds = feedService.findFeeds(pageable);

        List<FeedResponse> feedResponse = feeds.getContent()
                .stream()
                .map(FeedResponse::from)
                .collect(Collectors.toList());

        FeedResponses feedResponses = FeedResponses.builder()
                .feedResponses(feedResponse)
                .hasNext(feeds.hasNext())
                .nextPageable(feeds.nextPageable())
                .build();

        return ResponseEntity.ok(feedResponses);
    }
}
