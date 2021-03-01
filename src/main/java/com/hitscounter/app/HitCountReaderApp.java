package com.hitscounter.app;

import com.google.gson.Gson;
import com.hitscounter.app.config.Configurations;
import com.hitscounter.app.dto.HitCounts;
import com.hitscounter.app.service.HitsServices;
import com.hitscounter.app.service.impl.HitsServiceImpl;
import com.hitscounter.app.service.impl.RedisCacheServiceImpl;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HitCountReaderApp {
    private static final Logger LOG = LoggerFactory.getLogger(HitCountReaderApp.class);

    public static void main(String[] args) {
        String hostName = args[0];
        Configurations.init(hostName);

        HitsServices services =
            new HitsServiceImpl(new RedisCacheServiceImpl(Configurations.get()));

        while (true) {
            List<HitCounts> list = services.readHitsCounts();
            Gson gson = new Gson();
            System.out.println(gson.toJson(list));

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
