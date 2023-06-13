package com.wxw.springbootdemo.service.impl;

import com.wxw.springbootdemo.service.RedisListClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class RedisListClientImpl implements RedisListClient {

    @Resource
    private RedisTemplate<String, ArrayList<String>> redisTemplate;

    @Override
    public void set(String key, ArrayList<String> list) {
        //这里是向列表的右端添加
        redisTemplate.opsForList().rightPush(key, list);
    }

    @Override
    public ArrayList<String> get(String key, int start, int end) {
        List<ArrayList<String>> range = redisTemplate.opsForList().range(key, start, end);
        return (range == null || range.size() == 0) ? null : range.get(0);
    }
}
