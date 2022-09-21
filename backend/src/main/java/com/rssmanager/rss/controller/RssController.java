package com.rssmanager.rss.controller;

import com.rssmanager.rss.controller.dto.RssCreateRequest;
import com.rssmanager.rss.controller.dto.RssResponse;
import com.rssmanager.rss.controller.dto.RssResponses;
import com.rssmanager.rss.domain.Rss;
import com.rssmanager.rss.service.RssService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/rss")
@RestController
public class RssController {

    private final RssService rssService;

    public RssController(final RssService rssService) {
        this.rssService = rssService;
    }

    @GetMapping
    public ResponseEntity<RssResponses> findAll() {
        List<RssResponse> rssResponses = rssService.findAll()
                .stream()
                .map(RssResponse::from)
                .collect(Collectors.toList());
        RssResponses response = RssResponses.from(rssResponses);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody RssCreateRequest rssCreateRequest) throws URISyntaxException {
        Rss savedRss = rssService.save(rssCreateRequest);
        String createdLocation = String.format("/api/rss/%d", savedRss.getId());

        return ResponseEntity.created(new URI(createdLocation)).build();
    }
}
