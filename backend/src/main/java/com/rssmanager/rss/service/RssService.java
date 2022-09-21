package com.rssmanager.rss.service;

import com.rssmanager.rss.controller.dto.RssCreateRequest;
import com.rssmanager.rss.domain.Rss;
import java.util.List;

public interface RssService {

    List<Rss> findAll();

    Rss save(RssCreateRequest rssCreateRequest);
}
