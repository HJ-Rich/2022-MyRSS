package com.rssmanager.rss.controller;

import com.rssmanager.auth.annotation.LoginMember;
import com.rssmanager.member.domain.Member;
import com.rssmanager.rss.controller.dto.RssCreateRequest;
import com.rssmanager.rss.controller.dto.RssResponse;
import com.rssmanager.rss.controller.dto.RssResponses;
import com.rssmanager.rss.service.RssService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/rss")
@RestController
public class RssController {
    private final RssService rssService;

    public RssController(final RssService rssService) {
        this.rssService = rssService;
    }

    @GetMapping
    public ResponseEntity<RssResponses> findByMember(@LoginMember Member member) {
        final var rssResponses = rssService.findByMember(member)
                .stream()
                .map(RssResponse::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok(RssResponses.from(rssResponses));
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody final RssCreateRequest rssCreateRequest) throws URISyntaxException {
        final var savedRss = rssService.save(rssCreateRequest);
        final var createdLocation = String.format("/api/rss/%d", savedRss.getId());

        return ResponseEntity.created(new URI(createdLocation)).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> unsubscribe(@LoginMember Member member, @RequestParam Long id) {
        rssService.unsubscribe(member, id);
        return ResponseEntity.noContent().build();
    }
}
