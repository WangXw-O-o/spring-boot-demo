package com.wxw.springbootdemo.service.impl;

import com.wxw.springbootdemo.service.RedisLuaExecutor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class RedisLuaExecutorImpl implements RedisLuaExecutor {

    @Resource
    private StringRedisTemplate template;

    @Override
    public String testExecute() {
        String key = "test";
        String value = "test lua...";
        template.opsForValue().set(key, value, 100, TimeUnit.SECONDS);

        DefaultRedisScript<String> script = new DefaultRedisScript<>();
        script.setScriptText("return redis.call('get', KEYS[1])");
        script.setResultType(String.class);

        List<String> keyList = new ArrayList<>();
        keyList.add(key);

        return template.execute(script, keyList, value);
    }
}
