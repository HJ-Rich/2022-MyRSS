package com.rssmanager.rss.controller.dto;

import com.rssmanager.rss.domain.Bookmark;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BookmarkResponse {
    private Long id;
    private FeedResponse feed;

    public BookmarkResponse() {
    }

    @Builder
    public BookmarkResponse(final Long id, final FeedResponse feed) {
        this.id = id;
        this.feed = feed;
    }

    public static BookmarkResponse from(final Bookmark bookmark) {
        return BookmarkResponse.builder()
                .id(bookmark.getId())
                .feed(FeedResponse.from(bookmark.getFeed()))
                .build();
    }
}
