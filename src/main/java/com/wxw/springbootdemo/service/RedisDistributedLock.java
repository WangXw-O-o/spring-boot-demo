package com.wxw.springbootdemo.service;

/**
 * Redis分布式锁
 */
public interface RedisDistributedLock {

    /**
     * 加锁并设置超时时间，同时开启一个守护线程用于延长有效期
     * @param key        key
     * @param value      持有线程者标识
     * @param ex         过期时间 (单位：秒)
     */
    void lock(String key, String value, long ex);

    /**
     * 删除锁
     * 通过 Lua 脚本执行原子性删除
     */
    Long delLock(String key, String value);

}
