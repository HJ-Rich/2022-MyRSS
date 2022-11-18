package com.rssmanager.rss.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class DefaultRssParserTest {
    @ParameterizedTest(name = "{0} -> {1}")
    @CsvSource(value = {
            "https://creampuffy.tistory.com/rss, https://creampuffy.tistory.com/rss",
            "https://creampuffy.tistory.com/209, https://creampuffy.tistory.com/rss",
            "https://creampuffy.tistory.com, https://creampuffy.tistory.com/rss",
            "https://creampuffy.tistory.com/, https://creampuffy.tistory.com/rss",

            "creampuffy.tistory.com/rss, https://creampuffy.tistory.com/rss",
            "creampuffy.tistory.com/209, https://creampuffy.tistory.com/rss",
            "creampuffy.tistory.com, https://creampuffy.tistory.com/rss",
            "creampuffy.tistory.com/, https://creampuffy.tistory.com/rss",

            "https://v2.velog.io/rss/@richard7, https://v2.velog.io/rss/@richard7",
            "https://velog.io/@richard7/%EC%95%88%EB%85%95%ED%95%98%EC%84%B8%EC%9A%94-%EB%A6%AC%EC%B0%A8%EB%93%9C%EC%9E%85%EB%8B%88%EB%8B%A4, https://v2.velog.io/rss/@richard7",
            "https://velog.io/@richard7, https://v2.velog.io/rss/@richard7",
            "https://velog.io/@richard7/, https://v2.velog.io/rss/@richard7",

            "v2.velog.io/rss/@richard7, https://v2.velog.io/rss/@richard7",
            "velog.io/@richard7/%EC%95%88%EB%85%95%ED%95%98%EC%84%B8%EC%9A%94-%EB%A6%AC%EC%B0%A8%EB%93%9C%EC%9E%85%EB%8B%88%EB%8B%A4, https://v2.velog.io/rss/@richard7",
            "velog.io/@richard7, https://v2.velog.io/rss/@richard7",
            "velog.io/@richard7/, https://v2.velog.io/rss/@richard7",

            "https://brunch.co.kr/rss/@@5tdm, https://brunch.co.kr/rss/@@5tdm",
            "https://brunch.co.kr/@javajigi/27, https://brunch.co.kr/rss/@@5tdm",
            "https://brunch.co.kr/@javajigi, https://brunch.co.kr/rss/@@5tdm",
            "https://brunch.co.kr/@javajigi/, https://brunch.co.kr/rss/@@5tdm",

            "brunch.co.kr/rss/@@5tdm, https://brunch.co.kr/rss/@@5tdm",
            "brunch.co.kr/@javajigi/27, https://brunch.co.kr/rss/@@5tdm",
            "brunch.co.kr/@javajigi, https://brunch.co.kr/rss/@@5tdm",
            "brunch.co.kr/@javajigi/, https://brunch.co.kr/rss/@@5tdm",

            "https://medium.com/feed/@ztzy1907, https://medium.com/feed/@ztzy1907",
            "https://medium.com/@ztzy1907/hi-this-is-richard-e356cf3bc7f0, https://medium.com/feed/@ztzy1907",
            "https://medium.com/@ztzy1907, https://medium.com/feed/@ztzy1907",
            "https://medium.com/@ztzy1907/, https://medium.com/feed/@ztzy1907",

            "medium.com/feed/@ztzy1907, https://medium.com/feed/@ztzy1907",
            "medium.com/@ztzy1907/hi-this-is-richard-e356cf3bc7f0, https://medium.com/feed/@ztzy1907",
            "medium.com/@ztzy1907, https://medium.com/feed/@ztzy1907",
            "medium.com/@ztzy1907/, https://medium.com/feed/@ztzy1907",

            "https://ztzy1907.medium.com/feed, https://medium.com/feed/@ztzy1907",
            "https://ztzy1907.medium.com/hi-this-is-richard-e356cf3bc7f0, https://medium.com/feed/@ztzy1907",
            "https://ztzy1907.medium.com, https://medium.com/feed/@ztzy1907",
            "https://ztzy1907.medium.com/, https://medium.com/feed/@ztzy1907",

            "ztzy1907.medium.com/feed, https://medium.com/feed/@ztzy1907",
            "ztzy1907.medium.com/hi-this-is-richard-e356cf3bc7f0, https://medium.com/feed/@ztzy1907",
            "ztzy1907.medium.com, https://medium.com/feed/@ztzy1907",
            "ztzy1907.medium.com/, https://medium.com/feed/@ztzy1907"
    })
    void parse(final String requestUrl, final String expected) {
        // given
        final var parser = new DefaultRssParser();

        // when
        final var actual = parser.parse(requestUrl);

        // then
        assertThat(actual).isEqualTo(expected);
    }
}
