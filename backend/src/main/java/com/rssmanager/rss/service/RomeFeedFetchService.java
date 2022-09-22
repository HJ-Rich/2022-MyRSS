package com.rssmanager.rss.service;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.rssmanager.rss.domain.Feed;
import com.rssmanager.rss.domain.Rss;
import com.rssmanager.rss.repository.FeedRepository;
import com.rssmanager.rss.repository.RssRepository;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class RomeFeedFetchService implements FeedFetchService {

    private final RssRepository rssRepository;
    private final FeedRepository feedRepository;

    public RomeFeedFetchService(final RssRepository rssRepository, final FeedRepository feedRepository) {
        this.rssRepository = rssRepository;
        this.feedRepository = feedRepository;
    }

    @Transactional
    @Override
    public void saveNewFeedsByRss(String rssUrl) {
        SyndFeed rssInfo = fetchSyndFeed(rssUrl);
        Rss rss = createRssBySyndFeed(rssUrl, rssInfo);

        List<Feed> newFeedsToSave = rssInfo.getEntries()
                .stream()
                .filter(syndEntry -> !feedRepository.existsByLink(syndEntry.getLink()))
                .map(syndEntry -> createFeedFromSyndEntry(rss, syndEntry))
                .collect(Collectors.toList());

        feedRepository.saveAll(newFeedsToSave);
    }

    private static Feed createFeedFromSyndEntry(Rss rss, SyndEntry syndEntry) {
        Date date = syndEntry.getPublishedDate();
        if (Objects.isNull(date)) {
            date = syndEntry.getUpdatedDate();
        }
        if (Objects.isNull(date)) {
            date = new Date();
        }

        return Feed.builder()
                .title(syndEntry.getTitle())
                .updateDate(date)
                .link(syndEntry.getLink())
                .rss(rss)
                .build();
    }

    @Override
    public List<Feed> findFeedsByRss(String rssUrl) {

        return null;
    }

    @Override
    public Rss fetchRss(String rssUrl) {
        SyndFeed rssInfo = fetchSyndFeed(rssUrl);

        return createRssBySyndFeed(rssUrl, rssInfo);
    }

    private SyndFeed fetchSyndFeed(String rssUrl) {
        try {
            return new SyndFeedInput().build(new XmlReader(new URL(rssUrl)));
        } catch (FeedException | IOException e) {
            throw new RuntimeException("RSS를 조회하는 과정에서 예외가 발생했습니다", e);
        }
    }

    private Rss createRssBySyndFeed(String rssUrl, SyndFeed rssInfo) {
        return Rss.builder()
                .title(rssInfo.getTitle())
                .link(rssInfo.getLink())
                .iconUrl(rssInfo.getIcon().getUrl())
                .rssUrl(rssUrl)
                .recommended(false)
                .build();
    }
}
