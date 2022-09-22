package com.rssmanager.rss.domain;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class Feed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String link;
    private String description;
    private Date updateDate;
    @ManyToOne(fetch = FetchType.LAZY)
    private Rss rss;

    protected Feed() {
    }

    @Builder
    public Feed(final Long id, final String title, final String link, final String description, final Date updateDate,
                final Rss rss) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.description = description;
        this.updateDate = updateDate;
        this.rss = rss;
    }
}
