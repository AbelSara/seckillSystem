package com.seckill.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by Albert on 2020/3/8.
 */
@Service
public class RedisService {
    @Autowired
    JedisPool jedisPool;

    public <T> T get(KeyPrefix prefix, String key, Class<T> clazz) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            key = prefix.getPrefix() + '-' + key;
            String str = jedis.get(key);
            if (StringUtils.isEmpty(str))
                return null;
            T res = stringToBean(str, clazz);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            returnToPool(jedis);
        }
    }

    public boolean setNull(KeyPrefix prefix, String key) {
        Jedis jedis = jedisPool.getResource();
        key = prefix.getPrefix() + '-' + key;
        int second = prefix.expireSeconds();
        if (second == 0)
            jedis.set(key, "");
        else
            jedis.setex(key, second, "");
        return true;
    }

    public <T> boolean set(KeyPrefix prefix, String key, T value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String str = beanToString(value);
            if (str == null || str.length() == 0)
                return false;
            key = prefix.getPrefix() + '-' + key;
            int seconds = prefix.expireSeconds();
            if (seconds == 0)
                jedis.set(key, str);
            else
                jedis.setex(key, seconds, str);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            returnToPool(jedis);
        }
    }

    public boolean delete(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            key = prefix.getPrefix() + "-" + key;
            return jedis.del(key) > 0;
        } finally {
            returnToPool(jedis);
        }
    }

    public boolean exist(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            key = prefix.getPrefix() + "-" + key;
            return jedis.exists(key);
        } finally {
            returnToPool(jedis);
        }
    }

    public Long incr(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            key = prefix.getPrefix() + "-" + key;
            return jedis.incr(key);
        } finally {
            returnToPool(jedis);
        }
    }

    public Long decr(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            key = prefix.getPrefix() + "-" + key;
            return jedis.decr(key);
        } finally {
            returnToPool(jedis);
        }
    }

    public static <T> String beanToString(T value) {
        if (value == null)
            return null;
        Class<?> clazz = value.getClass();
        if (clazz == int.class || clazz == Integer.class)
            return "" + value;
        else if (clazz == long.class || clazz == Long.class)
            return "" + value;
        else if (clazz == String.class)
            return (String) value;
        else
            return JSON.toJSONString(value);
    }

    public static <T> T stringToBean(String str, Class<T> clazz) {
        if (str == null || str.length() == 0 || clazz == null)
            return null;
        if (clazz == int.class || clazz == Integer.class)
            return (T) Integer.valueOf(str);
        else if (clazz == long.class || clazz == Long.class)
            return (T) Long.valueOf(str);
        else if (clazz == String.class)
            return (T) str;
        else
            return JSON.parseObject(str, clazz);
    }

    private void returnToPool(Jedis jedis) {
        if (jedis != null)
            jedis.close();
    }
}
