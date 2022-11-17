package com.rssmanager.rss.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Getter
@Table(indexes = @Index(name = "idx_rss_rss_url", columnList = "rssUrl", unique = true))
@Entity
public class Rss {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String rssUrl;
    private String link;
    private String iconUrl;
    private boolean recommended = false;

    protected Rss() {
    }

    @Builder
    public Rss(final Long id, final String title, final String rssUrl, final String link, final String iconUrl,
               final boolean recommended) {
        this.id = id;
        this.title = title;
        this.rssUrl = rssUrl;
        this.link = link;
        this.iconUrl = iconUrl;
        this.recommended = recommended;
    }
}
