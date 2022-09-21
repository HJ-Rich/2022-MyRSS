package com.rssmanager.rss.service;

import com.rssmanager.rss.domain.Feed;
import com.rssmanager.rss.domain.Rss;
import java.util.List;

public interface FeedFetchService {

    void saveNewFeedsByRss(String rssUrl);

    List<Feed> findFeedsByRss(String rssUrl);

    Rss fetchRss(String rssUrl);
}
