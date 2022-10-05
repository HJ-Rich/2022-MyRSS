package com.rssmanager.rss.controller.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

@Getter
public class BookmarkResponses {
    private final List<BookmarkResponse> bookmarks;
    private final boolean hasNext;
    private final Pageable nextPageable;

    @Builder
    public BookmarkResponses(final List<BookmarkResponse> bookmarks, final boolean hasNext,
                             final Pageable nextPageable) {
        this.bookmarks = bookmarks;
        this.hasNext = hasNext;
        this.nextPageable = nextPageable;
    }
}
