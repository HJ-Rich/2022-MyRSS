package com.rssmanager.rss.service;

import static com.rssmanager.rss.domain.QBookmark.bookmark;
import static com.rssmanager.rss.domain.QFeed.feed;
import static com.rssmanager.rss.domain.QRss.rss;
import static com.rssmanager.rss.domain.QSubscribe.subscribe;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.rssmanager.exception.InvalidRssUrlException;
import com.rssmanager.member.domain.Member;
import com.rssmanager.rss.controller.dto.FeedResponse;
import com.rssmanager.rss.controller.dto.FeedResponses;
import com.rssmanager.rss.controller.dto.SubscribeRequest;
import com.rssmanager.rss.domain.Feed;
import com.rssmanager.rss.domain.Rss;
import com.rssmanager.rss.domain.Subscribe;
import com.rssmanager.rss.repository.FeedRepository;
import com.rssmanager.rss.repository.RssRepository;
import com.rssmanager.rss.repository.SubscribeRepository;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Transactional(readOnly = true)
@Service
public class JpaSubscribeService implements SubscribeService {
    private final SubscribeRepository subscribeRepository;
    private final FeedRepository feedRepository;
    private final RssRepository rssRepository;
    private final JPAQueryFactory jpaQueryFactory;
    private final RssParser parser;

    public JpaSubscribeService(final SubscribeRepository subscribeRepository, final FeedRepository feedRepository,
                               final RssRepository rssRepository, final JPAQueryFactory jpaQueryFactory,
                               final RssParser parser) {
        this.subscribeRepository = subscribeRepository;
        this.feedRepository = feedRepository;
        this.rssRepository = rssRepository;
        this.jpaQueryFactory = jpaQueryFactory;
        this.parser = parser;
    }

    @Transactional
    @Override
    public Long subscribe(final Member member, final SubscribeRequest subscribeRequest) {
        final var requestUrl = parser.parse(subscribeRequest.getUrl());

        final var foundRss = rssRepository.findByRssUrl(requestUrl);
        if (foundRss.isPresent()) {
            return registerSubscribe(member, foundRss.get());
        }

        final var feedInfo = fetchFeeds(requestUrl);
        final var savedRss = rssRepository.save(buildRss(feedInfo, requestUrl));
        feedRepository.saveAll(convertFeed(feedInfo, savedRss));

        return subscribeRepository.save(new Subscribe(member, savedRss)).getId();
    }

    private Long registerSubscribe(final Member member, final Rss rss) {
        final var id = subscribeRepository.findIdByRssIdAndMemberId(rss.getId(), member.getId());
        if (id.isPresent()) {
            return id.get();
        }

        final var subscribe = new Subscribe(member, rss);
        final var savedSubscribe = subscribeRepository.save(subscribe);

        return savedSubscribe.getId();
    }

    private SyndFeed fetchFeeds(final String requestUrl) {
        try {
            return new SyndFeedInput().build(new XmlReader(new URL(requestUrl)));
        } catch (Exception e) {
            throw new InvalidRssUrlException(e);
        }
    }

    private Rss buildRss(final SyndFeed feedInfo, final String rssUrl) {
        final var image = feedInfo.getImage();
        final var icon = feedInfo.getIcon();
        var iconUrl = "";

        if (Objects.nonNull(image) && StringUtils.hasText(image.getUrl())) {
            iconUrl = image.getUrl();
        }

        if (Objects.nonNull(icon) && StringUtils.hasText(icon.getUrl())) {
            iconUrl = icon.getUrl();
        }

        return Rss.builder()
                .title(feedInfo.getTitle())
                .rssUrl(rssUrl)
                .link(feedInfo.getLink())
                .iconUrl(iconUrl)
                .build();
    }

    private List<Feed> convertFeed(final SyndFeed feedInfo, final Rss savedRss) {
        return feedInfo.getEntries()
                .stream()
                .map(newFeed -> createFeed(savedRss, newFeed))
                .collect(Collectors.toList());
    }

    private Feed createFeed(final Rss rss, final SyndEntry newFeed) {
        return Feed.builder()
                .title(newFeed.getTitle())
                .link(URLDecoder.decode(newFeed.getLink(), StandardCharsets.UTF_8))
                .description(findDescription(newFeed))
                .updateDate(findDate(newFeed))
                .rss(rss)
                .build();
    }

    private String findDescription(final SyndEntry newFeed) {
        final var description = newFeed.getDescription();
        if (Objects.nonNull(description) && StringUtils.hasText(description.getValue())) {
            final String descriptionResult = description.getValue().replaceAll("<[^>]+>", "").strip();
            int length = descriptionResult.length();
            if (length > 500) {
                length = 500;
            }
            return descriptionResult.substring(0, length);
        }

        if (Objects.isNull(newFeed.getContents())) {
            return "";
        }

        final var contents = newFeed.getContents().get(0).getValue().replaceAll("<[^>]+>", "").strip();
        byte[] contentsBytes = contents.getBytes(StandardCharsets.UTF_8);
        int length = contentsBytes.length;
        if (length > 500) {
            length = 500;
        }
        return new String(contentsBytes, 0, length);
    }

    private Date findDate(final SyndEntry newFeed) {
        var updatedDate = newFeed.getPublishedDate();

        if (Objects.isNull(updatedDate)) {
            updatedDate = newFeed.getUpdatedDate();
        }
        if (Objects.isNull(updatedDate)) {
            updatedDate = new Date();
        }

        return updatedDate;
    }

    @Override
    public FeedResponses findSubscribedFeeds(final Member member, final Pageable pageable) {
        final var subscribedFeeds = findSubscribedFeedsByMember(member, pageable);

        final var feeds = convertToResponse(member, pageable, subscribedFeeds);
        final var hasNext = subscribedFeeds.size() > pageable.getPageSize();
        final var nextPageable = createNextPageable(pageable, hasNext);

        return FeedResponses.builder()
                .feedResponses(feeds)
                .hasNext(hasNext)
                .nextPageable(nextPageable)
                .build();
    }

    private List<Feed> findSubscribedFeedsByMember(final Member member, final Pageable pageable) {
        return jpaQueryFactory.selectFrom(feed)
                .leftJoin(feed.rss, rss).fetchJoin()
                .leftJoin(subscribe).on(feed.rss.id.eq(subscribe.rss.id))
                .where(subscribe.member.id.eq(member.getId()))
                .orderBy(feed.updateDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();
    }

    private List<FeedResponse> convertToResponse(final Member member, final Pageable pageable,
                                                 final List<Feed> subscribedFeeds) {
        final var bookmarkedFeedIds = new HashSet<>(
                jpaQueryFactory.select(bookmark.feed.id)
                        .from(bookmark)
                        .where(bookmark.member.id.eq(member.getId()))
                        .fetch()
        );

        return subscribedFeeds.stream()
                .limit(pageable.getPageSize())
                .map(feed -> FeedResponse.from(feed, bookmarkedFeedIds.contains(feed.getId())))
                .collect(Collectors.toList());
    }

    private Pageable createNextPageable(final Pageable pageable, final boolean hasNext) {
        var pageNumber = pageable.getPageNumber();

        if (hasNext) {
            pageNumber++;
        }

        return pageable.withPage(pageNumber);
    }
}
