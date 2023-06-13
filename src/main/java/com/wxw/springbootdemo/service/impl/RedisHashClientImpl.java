package com.wxw.springbootdemo.service.impl;

import com.wxw.springbootdemo.service.RedisHashClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RedisHashClientImpl implements RedisHashClient {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void set(String key, String fieldName, String fieldValue) {
        redisTemplate.opsForHash().put(key, fieldName, fieldValue);
    }

    @Override
    public String get(String key, String fieldName) {
        return (String) redisTemplate.opsForHash().get(key, fieldName);
    }
}
