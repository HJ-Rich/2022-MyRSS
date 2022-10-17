package com.rssmanager.rss.service;

import com.rssmanager.exception.InvalidRssUrlException;
import java.util.List;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

@Component
public class DefaultRssParser implements RssParser {
    private final List<RssExtractor> matchers = List.of(
            new TistoryExtractor(), new VelogExtractor(), new BrunchExtractor(), new MediumExtractor()
    );

    @Override
    public String parse(final String rss) {
        return matchers.stream()
                .filter(matcher -> matcher.support(rss))
                .findAny()
                .orElseThrow(InvalidRssUrlException::new)
                .parse(rss);
    }

    interface RssExtractor {
        boolean support(String rss);

        String parse(String rss);
    }

    class TistoryExtractor implements RssExtractor {
        private final Pattern tistory = Pattern.compile("([a-zA-Z0-9]+).tistory.com");

        @Override
        public boolean support(final String rss) {
            return rss.contains("tistory.com");
        }

        @Override
        public String parse(final String rss) {
            final var matcher = tistory.matcher(rss);
            matcher.find();
            return String.format("https://%s.tistory.com/rss", matcher.group(1));
        }
    }

    class VelogExtractor implements RssExtractor {
        private final List<Pattern> patterns = List.of(
                Pattern.compile("velog.io/@([a-zA-Z0-9]+)"),
                Pattern.compile("v2.velog.io/rss/@([a-zA-Z0-9]+)")
        );

        @Override
        public boolean support(final String rss) {
            return rss.contains("velog.io");
        }

        @Override
        public String parse(final String rss) {
            final var patternToUse = patterns.stream()
                    .filter(pattern -> pattern.matcher(rss).find())
                    .findAny()
                    .orElseThrow(InvalidRssUrlException::new);

            final var matcher = patternToUse.matcher(rss);
            matcher.find();
            return String.format("https://v2.velog.io/rss/@%s", matcher.group(1));
        }
    }

    class BrunchExtractor implements RssExtractor {
        private final Pattern rssPattern = Pattern.compile("brunch.co.kr/rss/@@([a-zA-Z0-9]+)");
        private final Pattern pagePattern = Pattern.compile("brunch.co.kr/@([a-zA-Z0-9]+)");

        @Override
        public boolean support(final String rss) {
            return rss.contains("brunch.co.kr");
        }

        @Override
        public String parse(final String rss) {
            if (rssPattern.matcher(rss).find()) {
                final var matcher = rssPattern.matcher(rss);
                matcher.find();
                return String.format("https://brunch.co.kr/rss/@@%s", matcher.group(1));
            }

            final var matcher = pagePattern.matcher(rss);
            matcher.find();
            final var brunchMain = String.format("https://brunch.co.kr/@%s", matcher.group(1));

            try {
                return Jsoup.connect(brunchMain)
                        .get()
                        .select("link[type=\"application/rss+xml\"]")
                        .attr("href");
            } catch (Exception e) {
                throw new InvalidRssUrlException(e);
            }
        }
    }

    class MediumExtractor implements RssExtractor {
        private final List<Pattern> patterns = List.of(
                Pattern.compile("medium.com/feed/@([a-zA-Z0-9]+)"),
                Pattern.compile("medium.com/@([a-zA-Z0-9]+)"),
                Pattern.compile("([a-zA-Z0-9]+).medium.com")
        );

        @Override
        public boolean support(final String rss) {
            return rss.contains("medium.com");
        }

        @Override
        public String parse(final String rss) {
            final var patternToUse = patterns.stream()
                    .filter(pattern -> pattern.matcher(rss).find())
                    .findAny()
                    .orElseThrow(InvalidRssUrlException::new);

            final var matcher = patternToUse.matcher(rss);
            matcher.find();
            return String.format("https://medium.com/feed/@%s", matcher.group(1));
        }
    }
}
