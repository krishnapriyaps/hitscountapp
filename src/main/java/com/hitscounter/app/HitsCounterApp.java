package com.hitscounter.app;

import com.hitscounter.app.dto.Hit;
import com.hitscounter.app.dto.HitCounts;
import com.hitscounter.app.service.CacheService;
import com.hitscounter.app.service.RedisCacheServiceImpl;
import java.util.List;

public class HitsCounterApp {
    public List<HitCounts> updateHitsCache(Hit hit) {
        CacheService cache = new RedisCacheServiceImpl();
        cache.addCount(hit);
        return cache.retrieveCount();
    }
}
