package com.rssmanager.rss.controller.dto;

import lombok.Getter;

@Getter
public class SubscribeResponse {
    private final Long id;

    public SubscribeResponse(final Long id) {
        this.id = id;
    }

    public static SubscribeResponse from(final Long id) {
        return new SubscribeResponse(id);
    }
}
