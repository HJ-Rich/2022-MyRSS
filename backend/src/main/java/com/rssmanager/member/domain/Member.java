package com.rssmanager.member.domain;

import com.rssmanager.exception.MemberProviderIdInvalidException;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
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
    @Column(name = "provider_id", length = 20, nullable = false, updatable = false, unique = true)
    private Long providerId;
    @Column(name = "name", length = 100)
    private String name;
    @Column(name = "image_url")
    private String imageUrl;

    protected Member() {
    }

    @Builder
    public Member(final Long id, final Long providerId, final String name, final String imageUrl) {
        this.id = id;
        this.providerId = providerId;
        this.name = name;
        this.imageUrl = imageUrl;

        validateProviderId();
    }

    private void validateProviderId() {
        if (Objects.isNull(this.providerId) || this.providerId < 1) {
            throw new MemberProviderIdInvalidException(this.providerId);
        }
    }
}
