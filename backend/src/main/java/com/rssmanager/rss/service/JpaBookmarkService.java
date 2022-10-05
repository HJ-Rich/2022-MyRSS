package com.rssmanager.rss.service;

import com.rssmanager.member.domain.Member;
import com.rssmanager.rss.controller.dto.BookmarkAddRequest;
import com.rssmanager.rss.domain.Bookmark;
import com.rssmanager.rss.domain.Feed;
import com.rssmanager.rss.repository.BookmarkRepository;
import org.springframework.data.domain.Page;
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

        if (exists(member, feed)) {
            return bookmarkRepository.findByMemberIdAndFeedId(member.getId(), feed.getId()).get();
        }

        return bookmarkRepository.save(new Bookmark(member, feed));
    }

    private boolean exists(final Member member, final Feed feed) {
        return bookmarkRepository.existsByMemberIdAndFeedId(member.getId(), feed.getId());
    }

    @Override
    public Page<Bookmark> findByMemberId(final Long memberId, final Pageable pageable) {
        return bookmarkRepository.findAllByMemberId(memberId, pageable);
    }
}
