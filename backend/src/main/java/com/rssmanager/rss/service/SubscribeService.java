package com.rssmanager.rss.service;

import com.rssmanager.member.domain.Member;
import com.rssmanager.rss.controller.dto.FeedResponses;
import com.rssmanager.rss.controller.dto.SubscribeRequest;
import org.springframework.data.domain.Pageable;

public interface SubscribeService {

    Long subscribe(Member member, SubscribeRequest subscribeRequest);

    FeedResponses findSubscribedFeeds(Member member, Pageable pageable);
}
