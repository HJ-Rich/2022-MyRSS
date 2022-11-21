package com.rssmanager.rss.controller;

import com.rssmanager.auth.support.LoginMember;
import com.rssmanager.member.domain.Member;
import com.rssmanager.rss.controller.dto.FeedResponses;
import com.rssmanager.rss.controller.dto.SubscribeRequest;
import com.rssmanager.rss.controller.dto.SubscribeResponse;
import com.rssmanager.rss.service.SubscribeService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/subscribes")
@RestController
public class SubscribeController {
    private final SubscribeService subscribeService;

    public SubscribeController(final SubscribeService subscribeService) {
        this.subscribeService = subscribeService;
    }

    @PostMapping
    public ResponseEntity<SubscribeResponse> subscribe(@LoginMember final Member member,
                                                       @RequestBody final SubscribeRequest subscribeRequest) {
        final var subscribeResult = subscribeService.subscribe(member, subscribeRequest);

        return new ResponseEntity<>(SubscribeResponse.from(subscribeResult), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<FeedResponses> readSubscribedFeeds(@LoginMember final Member member,
                                                             final Pageable pageable) {
        final var feedResponses = subscribeService.findSubscribedFeeds(member, pageable);
        return ResponseEntity.ok(feedResponses);
    }
}
