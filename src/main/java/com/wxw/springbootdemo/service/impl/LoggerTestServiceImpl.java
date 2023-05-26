package com.wxw.springbootdemo.service.impl;

import com.wxw.springbootdemo.service.LoggerTestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoggerTestServiceImpl implements LoggerTestService {

    @Override
    public void test(String param) {
        log.info("service logger param: {}", param);
        log.error("service logger param  >>> error: {}", param);
    }

}
