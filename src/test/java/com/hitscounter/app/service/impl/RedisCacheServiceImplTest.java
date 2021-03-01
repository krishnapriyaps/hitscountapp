package com.hitscounter.app.service.impl;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hitscounter.app.config.Configurations;
import com.hitscounter.app.dto.Hit;
import com.hitscounter.app.dto.HitCounts;
import com.hitscounter.app.service.CacheService;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@RunWith(MockitoJUnitRunner.class)
public class RedisCacheServiceImplTest {
    @Mock
    Configurations config;

    @Mock
    JedisPool jedisPool;

    @Mock
    Jedis jedis;

    CacheService service;

    @Before
    public void setUp() {
        service = new RedisCacheServiceImpl(config);
        when(config.getJedisPool()).thenReturn(jedisPool);
        when(jedisPool.getResource()).thenReturn(jedis);
    }

    @Test
    public void testAddCountTheFirstTimeUrl() {
        String userName = "userId123";
        Hit hit = Hit.HitBuilder.get()
            .withUserId(userName)
            .withUrl("https://www.example.org/birth/army?adjustment=bee&box=bike")
            .withType("POST")
            .withTimestamp(Instant.now().toEpochMilli())
            .build();

        String url = "www.example.org/birth/army";

        String urlUserKey = format("users:%s", url);
        String countKey = format("count:%s", url);

        when(jedis.exists(urlUserKey)).thenReturn(false);
        when(jedis.exists(countKey)).thenReturn(false);

        service.addCount(hit);

        Map<String, String> countObj = new HashMap<>();
        countObj.put("url", url);
        countObj.put("count", "1");

        verify(jedis, Mockito.times(1)).sadd(urlUserKey, userName);
        verify(jedis, Mockito.times(1)).hset(countKey, countObj);
    }

    @Test
    public void testAddCountWhenDifferentUserHitOnSameUrl() {
        String userName = "userId123";
        Hit hit = Hit.HitBuilder.get()
            .withUserId(userName)
            .withUrl("https://www.example.org/birth/army?adjustment=bee&box=bike")
            .withType("POST")
            .withTimestamp(Instant.now().toEpochMilli())
            .build();

        String url = "www.example.org/birth/army";

        String urlUserKey = format("users:%s", url);
        String countKey = format("count:%s", url);

        when(jedis.exists(urlUserKey)).thenReturn(true);
        when(jedis.sismember(urlUserKey, userName)).thenReturn(false);
        when(jedis.exists(countKey)).thenReturn(true);

        service.addCount(hit);

        Map<String, String> countObj = new HashMap<>();
        countObj.put("url", url);
        countObj.put("count", "1");

        verify(jedis, Mockito.times(1)).sadd(urlUserKey, userName);
        verify(jedis, Mockito.times(1)).hincrBy(countKey, "count", 1);
    }

    @Test
    public void testAddCountWhenUrlHit() {
        String userName = "userId123";
        Hit hit = Hit.HitBuilder.get()
            .withUserId(userName)
            .withUrl("https://www.example.org/birth/army?adjustment=bee&box=bike")
            .withType("POST")
            .withTimestamp(Instant.now().toEpochMilli())
            .build();

        String url = "www.example.org/birth/army";

        String urlUserKey = format("users:%s", url);
        String countKey = format("count:%s", url);

        when(jedis.exists(urlUserKey)).thenReturn(true);
        when(jedis.sismember(urlUserKey, userName)).thenReturn(false);
        when(jedis.exists(countKey)).thenReturn(false);

        service.addCount(hit);

        Map<String, String> countObj = new HashMap<>();
        countObj.put("url", url);
        countObj.put("count", "1");

        verify(jedis, Mockito.times(1)).sadd(urlUserKey, userName);
        verify(jedis, Mockito.times(1)).hset(countKey, countObj);
    }

    @Test
    public void testRetrieveCount() {
        when(jedis.keys("count:*"))
            .thenReturn(new HashSet<>(Arrays.asList("count:url123","count:url1234")));

        Map<String, String> obj1 = new HashMap<>();
        obj1.put("url", "url123");
        obj1.put("count", "2");

        Map<String, String> obj2 = new HashMap<>();
        obj2.put("url", "url1234");
        obj2.put("count", "1");

        when(jedis.hgetAll(eq("count:url123"))).thenReturn(obj1);
        when(jedis.hgetAll(eq("count:url1234"))).thenReturn(obj2);

        HitCounts hitCounts1 = new HitCounts("url123", "2");
        HitCounts hitCounts2 = new HitCounts("url1234", "1");

        List<HitCounts> response = service.retrieveCount();
        assertThat(response)
            .extracting(HitCounts::getUrl)
            .containsOnly("url123", "url1234");

        assertThat(response)
            .extracting(HitCounts::getUrl)
            .containsOnly("url123", "url1234");
    }
}
