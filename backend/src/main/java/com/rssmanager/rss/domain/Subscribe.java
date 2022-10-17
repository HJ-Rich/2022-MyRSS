package com.rssmanager.rss.domain;

import com.rssmanager.member.domain.Member;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class Subscribe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "memberId")
    private Member member;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rssId")
    private Rss rss;

    public Subscribe() {
    }

    @Builder
    public Subscribe(final Member member, final Rss rss) {
        this.member = member;
        this.rss = rss;
    }
}
