package com.wxw.springbootdemo.service;

/**
 * redis 操作 HashMap（hash表） 类型数据
 */
public interface RedisHashClient {

    void set(String key, String fieldName, String fieldValue);

    String get(String key, String fieldName);

}
