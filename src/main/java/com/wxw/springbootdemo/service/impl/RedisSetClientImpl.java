package com.wxw.springbootdemo.service.impl;

import com.wxw.springbootdemo.service.RedisSetClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

@Service
public class RedisSetClientImpl implements RedisSetClient {

    @Resource
    private RedisTemplate<String, String> redisTemplate;


    @Override
    public void set(String key, String[] values) {
        redisTemplate.opsForSet().add(key, values);
    }

    @Override
    public Set<String> getAllValues(String key) {
        return redisTemplate.opsForSet().members(key);
    }


}
