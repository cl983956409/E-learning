package com.learning.electronic.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 程龙[chenglonghy]
 * @date 2020/4/23 - 22:39
 * @history 2020/4/23 - 22:39 chenglonghy  create.
 */
@Service
public class RedisUtils {

    @Autowired
    private static RedisTemplate redisTemplate;

    public static boolean exist(String key) {
        return redisTemplate.hasKey(key);
    }

    public static void set(String key, String value) {
        redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    public static void set(String key, String value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    public static void set(String key, String value, long timeout, TimeUnit timeUtils) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUtils);
    }

    public static String get(String key) {
        return o2s(redisTemplate.opsForValue().get(key));
    }

    public static String hget(String key, String value) {
        return o2s(redisTemplate.opsForHash().get(key, value));
    }

    public static Map<String, String> hgetAll(String key) {
        HashOperations<String, String, String> vo = redisTemplate.opsForHash();
        Map<String, String> map = new HashMap<>();
        for (String haskey : vo.keys(key)) {
            String value = vo.get(key, haskey);
            map.put(haskey, value);
        }
        return map;
    }

    /*
    object 转 String
     */
    private static String o2s(Object obj) {
        return obj == null ? null : obj.toString();
    }
}
