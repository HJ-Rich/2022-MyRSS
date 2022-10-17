package com.rssmanager.auth.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class GithubUserInfoResponse {
    @JsonProperty("id")
    private Long providerId;
    private String name;
    @JsonProperty("avatar_url")
    private String imageUrl;

    private GithubUserInfoResponse() {
    }

    public GithubUserInfoResponse(final Long providerId, final String name, final String imageUrl) {
        this.providerId = providerId;
        this.name = name;
        this.imageUrl = imageUrl;
    }
}
