package com.rssmanager.member.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.rssmanager.exception.MemberProviderIdInvalidException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class MemberTest {
    @DisplayName("새로운 멤버를 생성할 수 있다")
    @Test
    void create_new_member() {
        // given
        final var providerId = 1L;
        final var name = "Richard";
        final var imageUrl = "richard.png";

        // when
        final var actual = new Member(null, providerId, name, imageUrl);

        // then
        assertAll(
                () -> assertThat(actual).isNotNull(),
                () -> assertThat(actual).usingRecursiveComparison()
                        .isEqualTo(new Member(null, providerId, name, imageUrl))
        );
    }

    @NullSource
    @ValueSource(longs = {-1L, 0L})
    @DisplayName("providerId가 null이거나 1 보다 작을 경우 예외가 발생한다")
    @ParameterizedTest(name = "providerId : {0}")
    void creating_new_member_with_invalid_providerId_should_fail(final Long invalidProviderId) {
        // given
        final var name = "Richard";
        final var imageUrl = "richard.png";

        // when & then
        assertThatThrownBy(() -> new Member(null, invalidProviderId, name, imageUrl))
                .isInstanceOf(MemberProviderIdInvalidException.class);
    }
}
