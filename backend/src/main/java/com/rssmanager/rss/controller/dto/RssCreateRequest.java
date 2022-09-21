package com.rssmanager.rss.controller.dto;

import lombok.Getter;

@Getter
public class RssCreateRequest {

    private String rssUrl;

    protected RssCreateRequest() {
    }

    public RssCreateRequest(final String rssUrl) {
        this.rssUrl = rssUrl;
    }
}
