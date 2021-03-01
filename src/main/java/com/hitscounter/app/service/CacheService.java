package com.hitscounter.app.service;

import com.hitscounter.app.dto.Hit;
import com.hitscounter.app.dto.HitCounts;
import java.util.List;

public interface CacheService {
    public boolean addCount(Hit hit);
    public List<HitCounts> retrieveCount();
}
