package com.hitscounter.app.service.impl;

import static java.lang.String.format;

import com.hitscounter.app.config.Configurations;
import com.hitscounter.app.dto.Hit;
import com.hitscounter.app.dto.HitCounts;
import com.hitscounter.app.service.CacheService;
import com.hitscounter.app.utils.UrlUtils;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import redis.clients.jedis.Jedis;

public class RedisCacheServiceImpl implements CacheService {
    private final Configurations config;
    private static String USER_LOOPUP_PREFIX = "users:";
    private static String COUNT_LOOPUP_PREFIX = "count:";
    private static String URL_FIELD_KEY = "url";
    private static String COUNT_FIELD_KEY = "count";

    public RedisCacheServiceImpl(Configurations config) {
        this.config = config;
    }

    private String getUrlUserLookUpKey(String url) throws URISyntaxException {
        String extractedUrl = UrlUtils.getUrl(url);
        return format("%s%s", USER_LOOPUP_PREFIX, extractedUrl);
    }

    private String getCountKey(String url) throws URISyntaxException {
        String extractedUrl = UrlUtils.getUrl(url);
        return format("%s%s", COUNT_LOOPUP_PREFIX, extractedUrl);
    }

    @Override
    public boolean addCount(Hit hit) {
        Jedis jedis = config.getJedisPool().getResource();

        try {
            String urlUserKey = getUrlUserLookUpKey(hit.getUrl());
            if (!jedis.exists(urlUserKey)
                || !jedis.sismember(urlUserKey, hit.getUserId())) {
                jedis.sadd(urlUserKey, hit.getUserId());

                String countKey = getCountKey(hit.getUrl());
                if (jedis.exists(countKey)) {
                    jedis.hincrBy(countKey, COUNT_FIELD_KEY, 1);
                } else {
                    Map<String, String> countObj = new HashMap<>();
                    countObj.put(URL_FIELD_KEY, UrlUtils.getUrl(hit.getUrl()));
                    countObj.put(COUNT_FIELD_KEY, "1");
                    jedis.hset(countKey, countObj);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        jedis.close();
        return true;
    }

    @Override
    public List<HitCounts> retrieveCount() {
        Jedis jedis =  config.getJedisPool().getResource();

        String pattern = format("%s*", COUNT_LOOPUP_PREFIX);
        Set<String> allUrlsKeys = jedis.keys(pattern);
        List<HitCounts> result = allUrlsKeys
            .parallelStream()
            .map((key) -> {
                Jedis jedisCon =  config.getJedisPool().getResource();
                Map<String, String> countObj = jedisCon.hgetAll(key);

                jedisCon.close();
                HitCounts hc = new HitCounts(
                    countObj.get(URL_FIELD_KEY),
                    countObj.get(COUNT_FIELD_KEY)
                );

                return hc;
            })
            .collect(Collectors.toList());
        jedis.close();
        return result;
    }
}
