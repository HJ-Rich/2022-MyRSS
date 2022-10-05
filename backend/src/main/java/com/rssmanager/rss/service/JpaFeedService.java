package com.rssmanager.rss.service;

import com.rssmanager.rss.controller.dto.FeedResponse;
import com.rssmanager.rss.controller.dto.FeedResponses;
import com.rssmanager.rss.domain.Feed;
import com.rssmanager.rss.repository.FeedRepository;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class JpaFeedService implements FeedService {
    private final FeedRepository feedRepository;

    public JpaFeedService(final FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    @Override
    public FeedResponses findFeeds(final Pageable pageable) {
        final var sort = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by("updateDate").descending());

        final var feeds = feedRepository.findAll(sort);

        final var feedResponse = feeds.getContent()
                .stream()
                .map(FeedResponse::from)
                .collect(Collectors.toList());

        return FeedResponses.builder()
                .feedResponses(feedResponse)
                .hasNext(feeds.hasNext())
                .nextPageable(feeds.nextPageable())
                .build();
    }

    @Override
    public Feed findById(final Long id) {
        return feedRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }
}
