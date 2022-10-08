package com.rssmanager.rss.repository;

import com.rssmanager.rss.domain.Subscribe;
import org.springframework.data.repository.Repository;

public interface SubscribeRepository extends Repository<Subscribe, Long> {
    Subscribe save(Subscribe subscribe);
}
