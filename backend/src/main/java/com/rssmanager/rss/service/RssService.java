package com.rssmanager.rss.service;

import com.rssmanager.member.domain.Member;
import com.rssmanager.rss.controller.dto.RssCreateRequest;
import com.rssmanager.rss.domain.Rss;
import java.util.List;

public interface RssService {
    List<Rss> findByMember(Member member);

    Rss save(RssCreateRequest rssCreateRequest);

    void unsubscribe(Member member, Long id);
}
