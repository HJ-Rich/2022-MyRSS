package com.rssmanager.rss.controller.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class FeedResponses {

    private final List<FeedResponse> feedResponses;

    public FeedResponses(final List<FeedResponse> feedResponses) {
        this.feedResponses = feedResponses;
    }

    public static FeedResponses from(List<FeedResponse> feedResponses) {
        return new FeedResponses(feedResponses);
    }
}
