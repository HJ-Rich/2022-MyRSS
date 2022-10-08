package com.rssmanager.rss.service;

import com.rssmanager.member.domain.Member;
import com.rssmanager.rss.controller.dto.SubscribeRequest;

public interface SubscribeService {

    Long subscribe(Member member, SubscribeRequest subscribeRequest);
}
