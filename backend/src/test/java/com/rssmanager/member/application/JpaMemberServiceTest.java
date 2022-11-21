package com.rssmanager.member.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.rssmanager.member.domain.Member;
import com.rssmanager.support.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

class JpaMemberServiceTest extends ServiceTest {
    @Autowired
    private MemberService memberService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @DisplayName("MemberService의 save메서드는")
    @Nested
    class Save {
        @DisplayName("신규 멤버를 저장할 수 있다")
        @Test
        void save_new_member() {
            // given
            final var member = new Member(null, 1L, "Richard", "richard.png");

            // when
            final var actual = memberService.save(member);

            // then
            assertAll(
                    () -> assertThat(actual).isNotNull(),
                    () -> assertThat(actual).isEqualTo(member)
            );
        }

        @DisplayName("providerId가 동일할 경우, 신규 저장없이 해당 멤버를 반환한다")
        @Test
        void return_saved_member_when_already_exists() {
            // given
            final var member = new Member(null, 1L, "Richard", "richard.png");
            final var saved = memberService.save(member);
            final var rows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "member");

            // when
            final var actual = memberService.save(new Member(null, 1L, "Richard", "richard.png"));
            final var rowsAfterSecondSave = JdbcTestUtils.countRowsInTable(jdbcTemplate, "member");

            // then
            assertAll(
                    () -> assertThat(saved).usingRecursiveComparison().isEqualTo(actual),
                    () -> assertThat(rows).isOne(),
                    () -> assertThat(rowsAfterSecondSave).isOne()
            );
        }
    }
}
