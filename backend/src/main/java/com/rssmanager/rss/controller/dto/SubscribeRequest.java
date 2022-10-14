package com.rssmanager.rss.controller.dto;

import lombok.Getter;

@Getter
public class SubscribeRequest {
    private String url;

    public SubscribeRequest() {
    }

    public SubscribeRequest(final String url) {
        this.url = url;
    }
}
