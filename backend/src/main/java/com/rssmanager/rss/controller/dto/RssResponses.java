package com.rssmanager.rss.controller.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class RssResponses {

    private final List<RssResponse> rssResponses;

    public RssResponses(final List<RssResponse> rssResponses) {
        this.rssResponses = rssResponses;
    }

    public static RssResponses from(List<RssResponse> rssResponses) {
        return new RssResponses(rssResponses);
    }
}
