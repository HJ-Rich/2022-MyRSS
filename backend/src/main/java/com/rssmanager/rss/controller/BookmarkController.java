package com.rssmanager.rss.controller;

import com.rssmanager.auth.annotation.LoginMember;
import com.rssmanager.member.domain.Member;
import com.rssmanager.rss.controller.dto.BookmarkAddRequest;
import com.rssmanager.rss.controller.dto.BookmarkAddResponse;
import com.rssmanager.rss.controller.dto.BookmarkResponse;
import com.rssmanager.rss.controller.dto.BookmarkResponses;
import com.rssmanager.rss.service.BookmarkService;
import java.util.stream.Collectors;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
        final var bookmarks = bookmarkService.findByMemberId(member.getId(), pageable);

        final var bookmarkResponse = bookmarks.getContent()
                .stream()
                .map(BookmarkResponse::from)
                .collect(Collectors.toList());

        final var bookmarkResponses = BookmarkResponses.builder()
                .bookmarks(bookmarkResponse)
                .hasNext(bookmarks.hasNext())
                .nextPageable(bookmarks.nextPageable())
                .build();

        return ResponseEntity.ok(bookmarkResponses);
    }
}
