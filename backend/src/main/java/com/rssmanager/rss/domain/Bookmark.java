package com.rssmanager.rss.domain;

import com.rssmanager.member.domain.Member;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Getter
@Table(indexes = @Index(name = "idx_bookmark_member_id_feed_id", columnList = "memberId, feedId", unique = true))
@Entity
public class Bookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId", foreignKey = @ForeignKey(name = "fk_bookmark_member_id"))
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feedId", foreignKey = @ForeignKey(name = "fk_bookmark_feed_id"))
    private Feed feed;

    protected Bookmark() {
    }

    @Builder
    public Bookmark(final Member member, final Feed feed) {
        this.member = member;
        this.feed = feed;
    }
}
