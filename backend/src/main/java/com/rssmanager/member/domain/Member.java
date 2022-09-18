package com.rssmanager.member.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class Member {

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
