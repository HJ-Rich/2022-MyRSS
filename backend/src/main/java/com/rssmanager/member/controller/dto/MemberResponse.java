package com.rssmanager.member.controller.dto;

import com.rssmanager.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberResponse {

    private final Long id;
    private final String nickname;
    private final String imageUrl;

    @Builder
    public MemberResponse(final Long id, final String nickname, final String imageUrl) {
        this.id = id;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
    }

    public static MemberResponse from(final Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .nickname(member.getName())
                .imageUrl(member.getImageUrl())
                .build();
    }
}
