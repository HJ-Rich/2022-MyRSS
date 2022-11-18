package com.rssmanager.rss.controller.dto;

import com.rssmanager.rss.domain.Rss;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RssResponse {
    private Long id;
    private String title;
    private String rssUrl;
    private String link;
    private String iconUrl;
    private boolean recommended;

    protected RssResponse() {
    }

    @Builder
    public RssResponse(final Long id, final String title, final String rssUrl, final String link, final String iconUrl,
                       final boolean recommended) {
        this.id = id;
        this.title = title;
        this.rssUrl = rssUrl;
        this.link = link;
        this.iconUrl = iconUrl;
        this.recommended = recommended;
    }

    public static RssResponse from(Rss rss) {
        return RssResponse.builder()
                .id(rss.getId())
                .title(rss.getTitle())
                .rssUrl(rss.getRssUrl())
                .link(rss.getLink())
                .iconUrl(rss.getIconUrl())
                .recommended(rss.isRecommended())
                .build();
    }
}
