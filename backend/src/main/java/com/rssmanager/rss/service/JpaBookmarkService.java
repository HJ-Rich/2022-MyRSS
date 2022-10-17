package com.rssmanager.rss.service;

import static com.rssmanager.rss.domain.QBookmark.bookmark;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rssmanager.member.domain.Member;
import com.rssmanager.rss.controller.dto.BookmarkAddRequest;
import com.rssmanager.rss.controller.dto.BookmarkResponse;
import com.rssmanager.rss.controller.dto.BookmarkResponses;
import com.rssmanager.rss.domain.Bookmark;
import com.rssmanager.rss.domain.Feed;
import com.rssmanager.rss.repository.BookmarkRepository;
import java.util.stream.Collectors;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class JpaBookmarkService implements BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final FeedService feedService;
    private final JPAQueryFactory jpaQueryFactory;

    public JpaBookmarkService(final BookmarkRepository bookmarkRepository, final FeedService feedService,
                              final JPAQueryFactory jpaQueryFactory) {
        this.bookmarkRepository = bookmarkRepository;
        this.feedService = feedService;
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Transactional
    @Override
    public Bookmark bookmark(final Member member, final BookmarkAddRequest bookmarkAddRequest) {
        final var feed = feedService.findById(bookmarkAddRequest.getId());

        if (exists(member, feed)) {
            return bookmarkRepository.findByMemberIdAndFeedId(member.getId(), feed.getId()).get();
        }

        return bookmarkRepository.save(new Bookmark(member, feed));
    }

    private boolean exists(final Member member, final Feed feed) {
        return bookmarkRepository.existsByMemberIdAndFeedId(member.getId(), feed.getId());
    }

    @Override
    public BookmarkResponses findByMemberId(final Member member, final Pageable pageable) {
        final var bookmarks = bookmarkRepository.findAllByMemberIdOrderByFeed_UpdateDateDesc(member.getId(), pageable);

        final var bookmarkResponse = bookmarks.getContent()
                .stream()
                .map(bookmark -> BookmarkResponse.from(bookmark, true))
                .collect(Collectors.toList());

        return BookmarkResponses.builder()
                .bookmarks(bookmarkResponse)
                .hasNext(bookmarks.hasNext())
                .nextPageable(bookmarks.nextPageable())
                .build();
    }

    @Transactional
    @Override
    public void unbookmark(final Member member, final Long feedId) {
        jpaQueryFactory.delete(bookmark)
                .where(bookmark.member.id.eq(member.getId()))
                .where(bookmark.feed.id.eq(feedId))
                .execute();
    }
}
