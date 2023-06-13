package com.wxw.springbootdemo.service.impl;

import com.wxw.springbootdemo.service.RedisStringClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class RedisStringClientImpl implements RedisStringClient {

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void set(String key, String value, long ex) {
        stringRedisTemplate.opsForValue().set(key, value, ex, TimeUnit.SECONDS);
    }

    @Override
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public void setManyValue(Map<String, String> manyValueMap) {
        stringRedisTemplate.opsForValue().multiSet(manyValueMap);
    }

    @Override
    public List<String> getManyValue(List<String> keyList) {
        return stringRedisTemplate.opsForValue().multiGet(keyList);
    }
}
