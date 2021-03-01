package com.hitscounter.app;

import com.hitscounter.app.config.Configurations;
import com.hitscounter.app.dto.Hit;
import com.hitscounter.app.publisher.RandomHitsProducer;
import com.hitscounter.app.service.HitsServices;
import com.hitscounter.app.service.impl.HitsServiceImpl;
import com.hitscounter.app.service.impl.RedisCacheServiceImpl;

/**
 * RandomHitsProducerApp Generated hits object every 5 seconds
 */
public class RandomHitsProducerApp {

    public static void main( String[] args ) {
        String hostName = args[0];
        Configurations.init(hostName);

        HitsServices services =
            new HitsServiceImpl(new RedisCacheServiceImpl(Configurations.get()));

        RandomHitsProducer hitsProducer = new RandomHitsProducer();

        while (true) {
            Hit hit = hitsProducer.generateHits();
            System.out.println(hit.toString());
            services.addNewHit(hit);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
