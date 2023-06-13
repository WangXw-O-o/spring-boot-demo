package com.wxw.springbootdemo.service;

import java.util.Set;

/**
 * redis 操作 ZSet（有序集合） 类型数据
 */
public interface RedisZSetClient {

    void set(String key, String value);

    Set<String> getAllValues(String key);

}
