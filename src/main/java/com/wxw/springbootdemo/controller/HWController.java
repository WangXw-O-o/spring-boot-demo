package com.wxw.springbootdemo.controller;

import com.wxw.springbootdemo.core.LoggerTestCore;
import com.wxw.springbootdemo.service.LoggerTestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
public class HWController {

    @Resource
    private LoggerTestService loggerTestService;
    @Resource
    private LoggerTestCore loggerTestCore;

    @GetMapping("/hello")
    public String helloWorld() {
        log.info("controller test HelloWorld");
        log.error("controller test HelloWorld >>> error");
        loggerTestService.test("HelloWorld");
        loggerTestCore.test("HelloWorld");
        return "HelloWorld";
    }

}
