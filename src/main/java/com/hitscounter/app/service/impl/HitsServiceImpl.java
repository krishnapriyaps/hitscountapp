package com.hitscounter.app.service.impl;

import com.hitscounter.app.dto.Hit;
import com.hitscounter.app.dto.HitCounts;
import com.hitscounter.app.service.CacheService;
import com.hitscounter.app.service.HitsServices;
import java.util.List;

public class HitsServiceImpl implements HitsServices {
    private CacheService cache;

    public HitsServiceImpl(CacheService cache) {
        this.cache = cache;
    }

    @Override
    public boolean addNewHit(Hit hit) {
        cache.addCount(hit);
        return true;
    }

    @Override
    public List<HitCounts> readHitsCounts() {
        return cache.retrieveCount();
    }
}
