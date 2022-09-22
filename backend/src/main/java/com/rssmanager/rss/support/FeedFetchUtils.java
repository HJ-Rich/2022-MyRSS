package com.rssmanager.rss.support;

import com.rssmanager.rss.domain.Feed;
import java.util.List;

public interface FeedFetchUtils {

    List<Feed> fetchFeedsByRss(String rssUrl);
}
