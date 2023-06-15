package com.wxw.springbootdemo.controller;

import com.wxw.springbootdemo.service.RedisDistributedLock;
import com.wxw.springbootdemo.service.RedisLuaExecutor;
import com.wxw.springbootdemo.service.RedisStringClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;

@RestController
@RequestMapping("/redis/test")
public class RedisController {

    @Resource
    private RedisStringClient redisClient;
    @Resource
    private RedisDistributedLock redisDistributedLock;
    @Resource
    private RedisLuaExecutor redisLuaExecutor;

    @GetMapping("set/get/string/{key}/{value}")
    public String testSetAndGetString(@PathVariable("key") String key,
                                      @PathVariable("value") String value) {
        redisClient.set(key, value, 100L);
        return redisClient.get(key);
    }

    @GetMapping("lock/simple")
    public String testSimpleLock() {
        try {
            String key = "test_lock";
            String value = UUID.randomUUID().toString();
            redisDistributedLock.lock(key, value, 1000);
            Thread.sleep(300);
            redisDistributedLock.delLock(key, value);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error : " + e.getMessage();
        }
    }

    @GetMapping("lua")
    public String testLuaExecute() {
        return redisLuaExecutor.testExecute();
    }

}
