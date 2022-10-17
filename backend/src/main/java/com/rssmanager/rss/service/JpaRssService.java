package com.rssmanager.rss.service;

import static com.rssmanager.rss.domain.QRss.rss;
import static com.rssmanager.rss.domain.QSubscribe.subscribe;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.rssmanager.member.domain.Member;
import com.rssmanager.rss.controller.dto.RssCreateRequest;
import com.rssmanager.rss.domain.Rss;
import com.rssmanager.rss.repository.RssRepository;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Transactional(readOnly = true)
@Service
public class JpaRssService implements RssService {
    private final RssRepository rssRepository;
    private final JPAQueryFactory jpaQueryFactory;

    public JpaRssService(final RssRepository rssRepository, final JPAQueryFactory jpaQueryFactory) {
        this.rssRepository = rssRepository;
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<Rss> findByMember(final Member member) {
        return jpaQueryFactory.selectFrom(rss)
                .leftJoin(subscribe)
                .on(subscribe.rss.id.eq(rss.id))
                .where(subscribe.member.id.eq(member.getId()))
                .fetch();
    }

    @Transactional
    @Override
    public Rss save(final RssCreateRequest rssCreateRequest) {
        SyndFeed feeds;
        try {
            feeds = new SyndFeedInput().build(new XmlReader(new URL(rssCreateRequest.getRssUrl())));
        } catch (FeedException | IOException e) {
            throw new RuntimeException(e);
        }

        final var newRssToSave = buildRss(rssCreateRequest, feeds);
        return rssRepository.save(newRssToSave);
    }

    @Transactional
    @Override
    public void unsubscribe(final Member member, final Long id) {
        jpaQueryFactory.delete(subscribe)
                .where(subscribe.member.id.eq(member.getId()))
                .where(subscribe.rss.id.eq(id))
                .execute();
    }

    private String findIconUrl(final SyndFeed feeds) {
        if (feeds.getIcon() == null || !StringUtils.hasText(feeds.getIcon().getLink())) {
            return RssIcons.from(feeds.getLink());
        }

        return feeds.getIcon().getLink();
    }

    private Rss buildRss(final RssCreateRequest rssCreateRequest, final SyndFeed feeds) {
        return Rss.builder()
                .title(feeds.getTitle())
                .rssUrl(rssCreateRequest.getRssUrl())
                .link(feeds.getUri())
                .iconUrl(findIconUrl(feeds))
                .build();
    }
}
