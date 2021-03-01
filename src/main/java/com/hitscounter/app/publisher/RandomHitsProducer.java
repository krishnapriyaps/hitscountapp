package com.hitscounter.app.publisher;

import com.hitscounter.app.dto.Hit;
import com.hitscounter.app.dto.Hit.HitBuilder;
import com.hitscounter.app.utils.RandomUtils;
import java.time.Instant;

public class RandomHitsProducer {
    public Hit generateHits() {
        String userId = RandomUtils.generateUserId();
        String url = RandomUtils.generateUrl();
        String type = RandomUtils.generateType();
        long timestamp = Instant.now().toEpochMilli();
        return HitBuilder.get()
            .withUserId(userId)
            .withUrl(url)
            .withType(type)
            .withTimestamp(timestamp)
            .build();
    }
}
