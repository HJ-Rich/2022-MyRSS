package com.rssmanager.rss.service;

import com.rssmanager.member.domain.Member;
import com.rssmanager.rss.controller.dto.BookmarkAddRequest;
import com.rssmanager.rss.domain.Bookmark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookmarkService {


    Bookmark bookmark(Member member, BookmarkAddRequest bookmarkAddRequest);

    Page<Bookmark> findByMemberId(Long memberId, Pageable pageable);
}
