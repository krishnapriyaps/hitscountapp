package com.hitscounter.app.config;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Configurations {

    private static Configurations config;
    private JedisPool jedisPool;
    private Configurations() {
    }

    public static Configurations init(String redisHost) {
        if(config == null) {
            config = new Configurations();
        }
        config.jedisPool = config.jedisPool(redisHost, 6379);
        return config;
    }

    public static Configurations init(
        String redisHost,
        int redisPort
    ) {
        if(config == null) {
            config = new Configurations();
        }
        config.jedisPool = config.jedisPool(redisHost, redisPort);
        return config;
    }

    public static Configurations get() {
        return config;
    }

    JedisPool jedisPool(String host, int port) {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(3);
        poolConfig.setMaxIdle(2);
        poolConfig.setMinIdle(1);

        return new JedisPool(poolConfig, host, port);
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }
}
