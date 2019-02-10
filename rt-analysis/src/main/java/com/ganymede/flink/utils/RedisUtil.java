package com.ganymede.flink.utils;

import redis.clients.jedis.Jedis;

public class RedisUtil {
    public static final Jedis jedis = new Jedis("spark1", 6379);

    public static String getByKey(String key) {
        return jedis.get(key);
    }

    public static void setKeyValue(String key, String value) {
        jedis.set(key, value);
    }

    public static void main(String[] args) {
        setKeyValue("c", 123 + "");
        System.out.println(getByKey("a"));
        System.out.println(getByKey("b"));
        System.out.println(getByKey("c"));
    }
}
