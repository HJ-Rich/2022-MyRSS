package com.rssmanager.documentation;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

import com.rssmanager.rss.domain.Rss;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

class RssControllerTest extends DocumentationTest {

    @Test
    void RSS_목록_조회() {
        when(rssService.findAll()).thenReturn(
                List.of(
                        new Rss(1L, "화음을 좋아하는 리차드", "https://creampuffy.tistory.com/rss",
                                "https://creampuffy.tistory.com", "", false),
                        new Rss(2L, "D2 Blog", "https://d2.naver.com/d2.atom", "https://d2.naver.com",
                                "https://d2.naver.com/favicon.ico", true),
                        new Rss(3L, "우아한형제들 기술블로그", "https://techblog.woowahan.com/feed/",
                                "https://techblog.woowahan.com",
                                "https://techblog.woowahan.com/wp-content/uploads/2020/08/favicon.ico", true)
                )
        );

        docsGiven
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/api/rss")
                .then().log().all()
                .apply(document("rss/list",
                        responseFields(
                                fieldWithPath("rssResponses.[].id").type(JsonFieldType.NUMBER)
                                        .description("RSS id"),
                                fieldWithPath("rssResponses.[].title").type(JsonFieldType.STRING)
                                        .description("RSS title"),
                                fieldWithPath("rssResponses.[].rssUrl").type(JsonFieldType.STRING)
                                        .description("RSS rssUrl"),
                                fieldWithPath("rssResponses.[].link").type(JsonFieldType.STRING)
                                        .description("RSS link"),
                                fieldWithPath("rssResponses.[].iconUrl").type(JsonFieldType.STRING)
                                        .description("RSS iconUrl"),
                                fieldWithPath("rssResponses.[].recommended").type(JsonFieldType.BOOLEAN)
                                        .description("RSS recommended")
                        )
                ))
                .statusCode(HttpStatus.OK.value());
    }
}
