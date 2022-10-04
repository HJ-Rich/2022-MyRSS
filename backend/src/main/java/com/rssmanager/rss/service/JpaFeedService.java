package com.rssmanager.rss.service;

import com.rssmanager.rss.controller.dto.FeedResponse;
import com.rssmanager.rss.controller.dto.FeedResponses;
import com.rssmanager.rss.domain.Feed;
import com.rssmanager.rss.repository.FeedRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class JpaFeedService implements FeedService {

    private final FeedRepository feedRepository;

    public JpaFeedService(FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    @Override
    public FeedResponses findFeeds(Pageable pageable) {
        final PageRequest sort = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by("updateDate").descending());

        final Page<Feed> feeds = feedRepository.findAll(sort);

        List<FeedResponse> feedResponse = feeds.getContent()
                .stream()
                .map(FeedResponse::from)
                .collect(Collectors.toList());

        FeedResponses feedResponses = FeedResponses.builder()
                .feedResponses(feedResponse)
                .hasNext(feeds.hasNext())
                .nextPageable(feeds.nextPageable())
                .build();

        return feedResponses;
    }

    @Override
    public Feed findById(final Long id) {
        return feedRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }
}
