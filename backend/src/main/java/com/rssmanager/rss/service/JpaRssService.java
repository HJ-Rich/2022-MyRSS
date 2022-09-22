package com.rssmanager.rss.service;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.rssmanager.rss.controller.dto.RssCreateRequest;
import com.rssmanager.rss.domain.Rss;
import com.rssmanager.rss.repository.RssRepository;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
@Service
public class JpaRssService implements RssService {

    private final RssRepository rssRepository;

    public JpaRssService(final RssRepository rssRepository) {
        this.rssRepository = rssRepository;
    }

    @Override
    public List<Rss> findAll() {
        return rssRepository.findAll();
    }

    @Override
    public Rss save(final RssCreateRequest rssCreateRequest) {
        SyndFeed feeds;
        try {
            feeds = new SyndFeedInput().build(new XmlReader(new URL(rssCreateRequest.getRssUrl())));
        } catch (FeedException | IOException e) {
            throw new RuntimeException(e);
        }

        final Rss newRssToSave = Rss.builder()
                .title(feeds.getTitle())
                .rssUrl(rssCreateRequest.getRssUrl())
                .link(feeds.getUri())
                .iconUrl(feeds.getIcon().getLink())
                .recommended(false)
                .build();

        return rssRepository.save(newRssToSave);
    }
}
