package com.wxw.springbootdemo.service.impl;

import com.wxw.springbootdemo.service.RedisZSetClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Slf4j
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
        BoundZSetOperations<String, String> operations = redisTemplate.opsForZSet().getOperations().boundZSetOps(key);
        return operations.range(0, -1);
    }

    @Override
    public Double getZSetMemberScore(String key, String memberKey) {
        return redisTemplate.opsForZSet().score(key, memberKey);
    }

    @Override
    public void incrZSetMemberScore(String key, String memberKey, Double score) {
        redisTemplate.opsForZSet().incrementScore(key, memberKey, score);
    }
}
