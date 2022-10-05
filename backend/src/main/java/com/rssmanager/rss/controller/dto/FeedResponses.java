package com.rssmanager.rss.controller.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

@Getter
public class FeedResponses {
    private final List<FeedResponse> feedResponses;
    private final boolean hasNext;
    private final Pageable nextPageable;

    @Builder
    public FeedResponses(final List<FeedResponse> feedResponses, final boolean hasNext, final Pageable nextPageable) {
        this.feedResponses = feedResponses;
        this.hasNext = hasNext;
        this.nextPageable = nextPageable;
    }
}
