package com.wxw.springbootdemo.service.impl;

import com.wxw.springbootdemo.service.RedisDistributedLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class SimpleRedisDistributedLockImpl implements RedisDistributedLock {

    //延长时间次数
    private static final int EXTENSION_TIMES_COUNT = 5;
    //守护线程扫描间隔（毫秒）
    private static final int SLEEP_TIME = 200;
    //每次延长的时间（毫秒）
    private static final int EXTEND_TIME = 200;

    private static DefaultRedisScript<Long> script;

    static {
        DefaultRedisScript<Long> defaultRedisScript = new DefaultRedisScript<>();
        defaultRedisScript.setScriptText("if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end");
        defaultRedisScript.setResultType(Long.class);
        script = defaultRedisScript;
    }

    @Resource
    private StringRedisTemplate template;

    @Override
    public void lock(String key, String value, long exTime) {
        log.info("加锁开始");
        //设置一个锁
        template.opsForValue().set(key, value, exTime, TimeUnit.MILLISECONDS);
        //开启一个守护线程延长过期时间
        startGuardThread(key, value, exTime);
    }

    private void startGuardThread(String lockKey, String value, long exTime) {
        new Thread(() -> {
            try {
                log.info("守护线程开始");
                int count = EXTENSION_TIMES_COUNT;
                //每次延长当前有效期的时间
                long nextExTime = exTime;
                while (count > 0) {
                    //守护线程等待
                    Thread.sleep(SLEEP_TIME);
                    //每次延长等待的时间
                    nextExTime += EXTEND_TIME;
                    String lockValue = template.opsForValue().get(lockKey);
                    if (lockValue == null) {
                        log.info("锁已释放");
                        return;
                    }
                    //还没有过期，并且仍然是当前守护的线程持有
                    if (value.equals(lockValue)) {
                        //重新设置有效期
                        log.info("有效期延长至 : {} ms", nextExTime);
                        template.opsForValue().set(lockKey, value, nextExTime, TimeUnit.MILLISECONDS);
                    }
                    count--;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public Long delLock(String key, String value) {
        try {
            log.info("删除锁开始");
            List<String> delKeyList = new ArrayList<>();
            delKeyList.add(key);
            Long result = template.execute(script, delKeyList, value);
            log.info("删除锁结果 : " + result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
