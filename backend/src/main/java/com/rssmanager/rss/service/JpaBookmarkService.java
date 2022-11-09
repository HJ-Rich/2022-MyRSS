package com.rssmanager.rss.service;

import com.rssmanager.member.domain.Member;
import com.rssmanager.rss.controller.dto.BookmarkAddRequest;
import com.rssmanager.rss.controller.dto.BookmarkResponse;
import com.rssmanager.rss.controller.dto.BookmarkResponses;
import com.rssmanager.rss.domain.Bookmark;
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

    public JpaBookmarkService(final BookmarkRepository bookmarkRepository, final FeedService feedService) {
        this.bookmarkRepository = bookmarkRepository;
        this.feedService = feedService;
    }

    @Transactional
    @Override
    public Bookmark bookmark(final Member member, final BookmarkAddRequest bookmarkAddRequest) {
        final var feed = feedService.findById(bookmarkAddRequest.getId());

        return bookmarkRepository.findFirstByMemberIdAndFeedId(member.getId(), feed.getId())
                .orElseGet(() -> bookmarkRepository.save(new Bookmark(member, feed)));
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
        final var bookmark = bookmarkRepository.findFirstByMemberIdAndFeedId(member.getId(), feedId)
                .orElseThrow();

        bookmarkRepository.deleteById(bookmark.getId());
    }
}
