package com.rssmanager.rss.service;

import com.rssmanager.member.domain.Member;
import com.rssmanager.rss.controller.dto.SubscribeRequest;
import com.rssmanager.rss.repository.FeedRepository;
import com.rssmanager.rss.repository.RssRepository;
import com.rssmanager.rss.repository.SubscribeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class JpaSubscribeService implements SubscribeService {
    private final SubscribeRepository subscribeRepository;
    private final FeedRepository feedRepository;
    private final RssRepository rssRepository;

    public JpaSubscribeService(final SubscribeRepository subscribeRepository, final FeedRepository feedRepository,
                               final RssRepository rssRepository) {
        this.subscribeRepository = subscribeRepository;
        this.feedRepository = feedRepository;
        this.rssRepository = rssRepository;
    }

    @Transactional
    @Override
    public Long subscribe(final Member member, final SubscribeRequest subscribeRequest) {
        final var requestUrl = subscribeRequest.getUrl();
        // 등록된 RSS인지 확인하고 등록되어 있다면 구독리포에 저장하고 리턴하면 끝이다.
        // 처음 등록하는 RSS이면 RSS 저장, Feed저장, 구독 저장 셋다해야한다.
//
//        final Optional<Rss> byRssUrl = rssRepository.findByRssUrl(requestUrl);
//        if (byRssUrl.isPresent()) {
//            final var subscribe = subscribeRepository.save(new Subscribe(member, byRssUrl.get()));
//            return subscribe.getId();
//        }
//
//        final var newFeedsToSave = new ArrayList<Feed>();
//
//        final SyndFeed feeds;
//        try {
//            feeds = new SyndFeedInput().build(new XmlReader(new URL(requestUrl)));
//        } catch (FeedException | IOException e) {
//            throw new RuntimeException(e);
//        }
//        final var newFeeds = filterNewFeeds(rss, feeds);
//        newFeedsToSave.addAll(newFeeds);
//
//        if (newFeedsToSave.isEmpty()) {
//            return;
//        }
//        rssRepository.saveAll(newFeedsToSave);
//
//        subscribeRepository.save(new Sub)
        return null;
    }
}
