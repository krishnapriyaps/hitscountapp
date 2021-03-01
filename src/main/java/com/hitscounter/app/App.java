package com.hitscounter.app;

import com.hitscounter.app.dto.Hit;
import com.hitscounter.app.dto.HitCounts;
import com.hitscounter.app.utils.RandomUtils;
import java.time.Instant;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        String hostName = args[0];
        Configurations.init(hostName, 6379);

        for(int i =0 ; i <10; i++) {
            String userId = RandomUtils.generateUserId();
            String url = RandomUtils.generateUrl();
            String type = RandomUtils.generateType();
            long timestamp = Instant.now().toEpochMilli();
            Hit hit = Hit.HitBuilder.get()
                .withUserId(userId)
                .withUrl(url)
                .withType(type)
                .withTimestamp(timestamp)
                .build();
            System.out.println(hit);
            HitsCounterApp app = new HitsCounterApp();
            List<HitCounts> counts = app.updateHitsCache(hit);
            counts.forEach(System.out::println);
        }
    }
}
