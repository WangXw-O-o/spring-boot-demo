package com.wxw.springbootdemo.core.impl;

import com.wxw.springbootdemo.core.LoggerTestCore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoggerTestCoreImpl implements LoggerTestCore {
    @Override
    public void test(String param) {
        log.info("core logger param: {}", param);
        log.error("core logger param  >>> error: {}", param);
    }
}
