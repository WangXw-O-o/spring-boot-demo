package com.wxw.springbootdemo.service.impl;

import com.wxw.springbootdemo.service.RedisZSetClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

@Service
public class RedisZSetClientImpl implements RedisZSetClient {
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void set(String key, String value) {
        redisTemplate.opsForZSet().add(key, value, 1);
    }

    @Override
    public Set<String> getAllValues(String key) {
        return null;
    }
}
