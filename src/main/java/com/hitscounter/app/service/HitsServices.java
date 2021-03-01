package com.hitscounter.app.service;

import com.hitscounter.app.dto.Hit;
import com.hitscounter.app.dto.HitCounts;
import java.util.List;

public interface HitsServices {
    boolean addNewHit(Hit hit);
    List<HitCounts> readHitsCounts();
}
