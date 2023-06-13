package com.wxw.springbootdemo.service;

import java.util.Set;

/**
 * redis 操作 Set（集合） 类型数据
 */
public interface RedisSetClient {

    void set(String key, String[] values);

    Set<String> getAllValues(String key);
}
