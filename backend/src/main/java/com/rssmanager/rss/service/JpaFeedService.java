package com.rssmanager.rss.service;

import com.rssmanager.rss.domain.Feed;
import com.rssmanager.rss.repository.FeedRepository;
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
    public Page<Feed> findFeeds(Pageable pageable) {
        final PageRequest sort = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by("updateDate").descending());

        return feedRepository.findAll(sort);
    }
}
