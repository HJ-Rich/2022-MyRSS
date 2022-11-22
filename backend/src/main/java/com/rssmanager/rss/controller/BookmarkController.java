package com.rssmanager.rss.controller;

import com.rssmanager.auth.support.LoginMember;
import com.rssmanager.member.domain.Member;
import com.rssmanager.rss.controller.dto.BookmarkAddRequest;
import com.rssmanager.rss.controller.dto.BookmarkAddResponse;
import com.rssmanager.rss.controller.dto.BookmarkResponses;
import com.rssmanager.rss.service.BookmarkService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/api/bookmarks")
@RestController
public class BookmarkController {
    private final BookmarkService bookmarkService;

    public BookmarkController(final BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    @PostMapping
    public ResponseEntity<BookmarkAddResponse> bookmark(@LoginMember final Member member,
                                                        @RequestBody final BookmarkAddRequest bookmarkAddRequest) {
        final var bookmark = bookmarkService.bookmark(member, bookmarkAddRequest);
        return ResponseEntity.ok(BookmarkAddResponse.from(bookmark));
    }

    @GetMapping
    public ResponseEntity<BookmarkResponses> findAllByMemberId(@LoginMember final Member member,
                                                               final Pageable pageable) {
        final var bookmarkResponses = bookmarkService.findByMemberId(member, pageable);
        return ResponseEntity.ok(bookmarkResponses);
    }

    @DeleteMapping
    public ResponseEntity<Void> unbookmark(@LoginMember final Member member,
                                           @RequestParam("feedId") final Long feedId) {
        bookmarkService.unbookmark(member, feedId);
        return ResponseEntity.noContent().build();
    }
}
