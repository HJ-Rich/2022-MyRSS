package com.rssmanager.rss.controller.dto;

import lombok.Getter;

@Getter
public class BookmarkAddRequest {
    private Long id;

    public BookmarkAddRequest() {
    }

    public BookmarkAddRequest(final Long id) {
        this.id = id;
    }
}
