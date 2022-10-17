package com.rssmanager.rss.service;

import com.rssmanager.member.domain.Member;
import com.rssmanager.rss.controller.dto.BookmarkAddRequest;
import com.rssmanager.rss.controller.dto.BookmarkResponses;
import com.rssmanager.rss.domain.Bookmark;
import org.springframework.data.domain.Pageable;

public interface BookmarkService {
    Bookmark bookmark(Member member, BookmarkAddRequest bookmarkAddRequest);

    BookmarkResponses findByMemberId(Member member, Pageable pageable);

    void unbookmark(Member member, Long bookmarkId);
}
