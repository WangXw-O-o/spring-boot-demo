package com.wxw.springbootdemo.service;

import java.util.List;
import java.util.Map;

/**
 * redis 操作 String 类型数据
 */
public interface RedisStringClient {

    void set(String key, String value, long ex);

    String get(String key);

    //批量插入数据，如果想要同时设置过期时间，那么就需要使用Pipeline，或者采用Lua脚本去执行
    void setManyValue(Map<String, String> manyValueMap);

    //批量获取数据
    List<String> getManyValue(List<String> keyList);
}
