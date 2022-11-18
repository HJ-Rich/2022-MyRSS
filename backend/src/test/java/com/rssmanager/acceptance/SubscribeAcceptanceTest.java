package com.rssmanager.acceptance;

import static org.junit.jupiter.api.Assertions.assertAll;

import com.rssmanager.rss.controller.dto.RssResponses;
import com.rssmanager.rss.controller.dto.SubscribeRequest;
import com.rssmanager.rss.controller.dto.SubscribeResponse;
import org.junit.jupiter.api.Test;

public class SubscribeAcceptanceTest extends AcceptanceTest {
    private static final String RSS_API_URL = "/api/subscribes";

    @Test
    void 로그인된_회원은_신규_RSS를_구독할_수_있다() {
        // given
        final var 쿠키 = 로그인();
        final var RSS_등록_요청 = new SubscribeRequest("https://creampuffy.tistory.com/111");

        // when
        final var RSS_구독_응답 = 인증된_생성요청(RSS_API_URL, 쿠키, RSS_등록_요청);
        final var RSS_구독_결과 = RSS_구독_응답.body().as(SubscribeResponse.class);

        // then
        assertAll(
                생성_응답(RSS_구독_응답),
                단일_데이터_검증(RSS_구독_결과, "id", 1L)
        );
    }

    @Test
    void 로그인된_회원은_구독중인_RSS_목록을_조회할_수_있다() {
        // given
        final var 쿠키 = 로그인();
        final var RSS_등록_요청 = new SubscribeRequest("https://creampuffy.tistory.com/111");
        인증된_생성요청(RSS_API_URL, 쿠키, RSS_등록_요청);

        // when
        final var RSS_조회_응답 = 인증된_조회요청("/api/rss", 쿠키);
        final var RSS_조회_결과 = RSS_조회_응답.body().as(RssResponses.class).getRssResponses();

        // then
        assertAll(
                정상_응답(RSS_조회_응답),
                리스트_데이터_검증(RSS_조회_결과, "link", "https://creampuffy.tistory.com/"),
                리스트_데이터_검증(RSS_조회_결과, "rssUrl", "https://creampuffy.tistory.com/rss")
        );
    }
}
