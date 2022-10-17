package com.rssmanager.rss.controller.dto;

import com.rssmanager.rss.domain.Bookmark;
import lombok.Getter;

@Getter
public class BookmarkAddResponse {
    private final Long id;

    public BookmarkAddResponse(final Long id) {
        this.id = id;
    }

    public static BookmarkAddResponse from(final Bookmark bookmark) {
        return new BookmarkAddResponse(bookmark.getId());
    }
}
