package com.wxw.springbootdemo.service;

import java.util.ArrayList;

/**
 * redis 操作 List（列表） 类型数据
 */
public interface RedisListClient {

    void set(String key, ArrayList<String> list);

    ArrayList<String> get(String key, int start, int end);
}
