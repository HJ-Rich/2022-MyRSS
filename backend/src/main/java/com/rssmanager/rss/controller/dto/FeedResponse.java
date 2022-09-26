package com.rssmanager.rss.controller.dto;

import com.rssmanager.rss.domain.Feed;
import com.rssmanager.rss.domain.Rss;
import java.util.Date;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FeedResponse {

    private final Long id;
    private final String title;
    private final String link;
    private final String description;
    private final Date updateDate;
    private final RssResponse rss;

    @Builder
    public FeedResponse(final Long id, final String title, final String link, final String description,
                        final Date updateDate, final RssResponse rss) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.description = description;
        this.updateDate = updateDate;
        this.rss = rss;
    }

    public static FeedResponse from(Feed feed) {
        final Rss rss = feed.getRss();
        final RssResponse rssResponse = RssResponse.from(rss);

        return FeedResponse.builder()
                .id(feed.getId())
                .title(feed.getTitle())
                .link(feed.getLink())
                .description(feed.getDescription())
                .updateDate(feed.getUpdateDate())
                .rss(rssResponse)
                .build();
    }
}
