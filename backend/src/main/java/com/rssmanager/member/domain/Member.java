package com.rssmanager.member.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Getter
@Table(indexes = @Index(name = "idx_member_provider_id", columnList = "providerId", unique = true))
@Entity
public class Member implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long providerId;
    private String name;
    private String imageUrl;

    protected Member() {
    }

    @Builder
    public Member(final Long id, final Long providerId, final String name, final String imageUrl) {
        this.id = id;
        this.providerId = providerId;
        this.name = name;
        this.imageUrl = imageUrl;
    }
}
