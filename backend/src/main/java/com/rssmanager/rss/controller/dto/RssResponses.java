package com.rssmanager.rss.controller.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class RssResponses {
    private List<RssResponse> rssResponses;

    protected RssResponses() {
    }

    public RssResponses(final List<RssResponse> rssResponses) {
        this.rssResponses = rssResponses;
    }

    public static RssResponses from(final List<RssResponse> rssResponses) {
        return new RssResponses(rssResponses);
    }
}
