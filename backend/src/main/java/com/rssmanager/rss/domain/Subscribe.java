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
@Table(indexes = @Index(name = "idx_subscribe_member_id_rss_id", columnList = "memberId, rssId", unique = true))
@Entity
public class Subscribe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "memberId", foreignKey = @ForeignKey(name = "fk_subscribe_member_id"))
    private Member member;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rssId", foreignKey = @ForeignKey(name = "fk_subscribe_rss_id"))
    private Rss rss;

    public Subscribe() {
    }

    @Builder
    public Subscribe(final Member member, final Rss rss) {
        this.member = member;
        this.rss = rss;
    }
}
