package com.rssmanager.rss.controller.dto;

import lombok.Getter;

@Getter
public class SubscribeResponse {
    private Long id;

    public SubscribeResponse() {
    }

    public SubscribeResponse(final Long id) {
        this.id = id;
    }

    public static SubscribeResponse from(final Long id) {
        return new SubscribeResponse(id);
    }
}
