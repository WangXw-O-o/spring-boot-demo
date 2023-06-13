package com.wxw.springbootdemo.controller;

import com.wxw.springbootdemo.service.RedisStringClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/redis")
public class RedisController {

    @Resource
    private RedisStringClient redisClient;

    @GetMapping("test/set/get/string/{key}/{value}")
    public String testSetAndGetString(@PathVariable("key") String key,
                                      @PathVariable("value") String value) {
        redisClient.set(key, value, 100L);
        return redisClient.get(key);
    }

}
