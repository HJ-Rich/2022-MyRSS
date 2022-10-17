package com.rssmanager.rss.service;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;

public enum RssIcons {
    TISTORY(rssLink -> rssLink.contains("tistory.com"), rssLink -> String.format("%s/favicon.ico", rssLink)),
    NONE(rssLink -> false, rssLink -> ""),
    ;

    private final Predicate<String> rssName;
    private final Function<String, String> iconUrlCreator;

    RssIcons(final Predicate<String> rssName, final Function<String, String> iconUrlCreator) {
        this.rssName = rssName;
        this.iconUrlCreator = iconUrlCreator;
    }

    public static String from(final String rssLink) {
        return Arrays.stream(values())
                .filter(rssIcon -> rssIcon.rssName.test(rssLink))
                .findAny()
                .orElse(NONE)
                .iconUrlCreator.apply(rssLink);
    }
}
