package com.rssmanager.rss.service;

import static com.rssmanager.rss.domain.QBookmark.bookmark;
import static com.rssmanager.rss.domain.QFeed.feed;
import static com.rssmanager.rss.domain.QRss.rss;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rssmanager.rss.controller.dto.FeedResponse;
import com.rssmanager.rss.controller.dto.FeedResponses;
import com.rssmanager.rss.domain.Feed;
import com.rssmanager.rss.repository.FeedRepository;
import com.rssmanager.util.SessionManager;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class JpaFeedService implements FeedService {
    private final FeedRepository feedRepository;
    private final JPAQueryFactory jpaQueryFactory;
    private final SessionManager sessionManager;

    public JpaFeedService(final FeedRepository feedRepository, final JPAQueryFactory jpaQueryFactory,
                          final SessionManager sessionManager) {
        this.feedRepository = feedRepository;
        this.jpaQueryFactory = jpaQueryFactory;
        this.sessionManager = sessionManager;
    }

    @Override
    public FeedResponses findFeeds(final Pageable pageable) {
        final var feeds = findRecommendedFeeds(pageable);

        final var feedResponses = convertToResponse(pageable, feeds);
        final var hasNext = feeds.size() > pageable.getPageSize();
        final var nextPageable = createNextPageable(pageable, hasNext);

        return FeedResponses.builder()
                .feedResponses(feedResponses)
                .hasNext(hasNext)
                .nextPageable(nextPageable)
                .build();
    }

    private List<Feed> findRecommendedFeeds(final Pageable pageable) {
        return jpaQueryFactory.selectFrom(feed)
                .leftJoin(feed.rss, rss)
                .fetchJoin()
                .where(rss.recommended.isTrue())
                .orderBy(feed.updateDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();
    }

    private List<FeedResponse> convertToResponse(final Pageable pageable, final List<Feed> feeds) {
        final var member = sessionManager.getLoginMember();
        if (Objects.isNull(member)) {
            return feeds.stream()
                    .limit(pageable.getPageSize())
                    .map(feed -> FeedResponse.from(feed, false))
                    .collect(Collectors.toList());
        }

        final var bookmakredFeedIds = new HashSet<>(
                jpaQueryFactory.select(bookmark.feed.id)
                        .from(bookmark)
                        .where(bookmark.member.id.eq(member.getId()))
                        .fetch()
        );

        return feeds.stream()
                .limit(pageable.getPageSize())
                .map(feed -> FeedResponse.from(feed, bookmakredFeedIds.contains(feed.getId())))
                .collect(Collectors.toList());
    }

    private Pageable createNextPageable(final Pageable pageable, final boolean hasNext) {
        var pageNumber = pageable.getPageNumber();

        if (hasNext) {
            pageNumber++;
        }

        return pageable.withPage(pageNumber);
    }

    @Override
    public Feed findById(final Long id) {
        return feedRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }
}
