package com.rssmanager.rss;

import com.rometools.rome.feed.synd.SyndContent;
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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class FeedFetchJob {

    private static final int SECOND = 1000;
    private static final int HOUR = SECOND * 3600;

    private final RssRepository rssRepository;
    private final FeedRepository feedRepository;

    public FeedFetchJob(final RssRepository rssRepository, final FeedRepository feedRepository) {
        this.rssRepository = rssRepository;
        this.feedRepository = feedRepository;
    }

    @Async
    @Scheduled(fixedDelay = HOUR)
    public void fetchFeeds() throws IOException, FeedException {
        List<Rss> registeredRss = rssRepository.findAll();
        List<Feed> newFeedsToSave = new ArrayList<>();

        for (Rss rss : registeredRss) {
            SyndFeed feeds = new SyndFeedInput().build(new XmlReader(new URL(rss.getRssUrl())));
            List<Feed> newFeeds = filterNewFeeds(rss, feeds);
            newFeedsToSave.addAll(newFeeds);
        }

        if (newFeedsToSave.isEmpty()) {
            return;
        }
        rssRepository.saveAll(newFeedsToSave);
    }

    private List<Feed> filterNewFeeds(final Rss rss, final SyndFeed feeds) {
        return feeds.getEntries()
                .stream()
                .filter(feed -> !feedRepository.existsByLink(feed.getLink()))
                .map(newFeed -> createFeed(rss, newFeed))
                .collect(Collectors.toList());
    }

    private Feed createFeed(Rss rss, SyndEntry newFeed) {
        return Feed.builder()
                .title(newFeed.getTitle())
                .link(newFeed.getLink())
                .description(findDescription(newFeed))
                .updateDate(findDate(newFeed))
                .rss(rss)
                .build();
    }

    private String findDescription(SyndEntry newFeed) {
        SyndContent description = newFeed.getDescription();
        if (Objects.nonNull(description) && StringUtils.hasText(description.getValue())) {
            final String descriptionResult = description.getValue().replaceAll("<[^>]+>", "").strip();
            int length = descriptionResult.length();
            if (length > 500) {
                length = 500;
            }
            return descriptionResult.substring(0, length);
        }

        if (Objects.isNull(newFeed.getContents())) {
            return "";
        }

        String contents = newFeed.getContents().get(0).getValue().replaceAll("<[^>]+>", "").strip();
        byte[] contentsBytes = contents.getBytes(StandardCharsets.UTF_8);
        int length = contentsBytes.length;
        if (length > 500) {
            length = 500;
        }
        String descriptionResult = new String(contentsBytes, 0, length);
        return descriptionResult;
    }

    private Date findDate(SyndEntry newFeed) {
        Date updatedDate = newFeed.getPublishedDate();

        if (Objects.isNull(updatedDate)) {
            updatedDate = newFeed.getUpdatedDate();
        }
        if (Objects.isNull(updatedDate)) {
            updatedDate = new Date();
        }

        return updatedDate;
    }
}
