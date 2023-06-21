package com.wxw.springbootdemo;

import com.wxw.springbootdemo.aop.MyAopAnnotation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("test/{isThrow}")
    @MyAopAnnotation
    public String test(@PathVariable("isThrow") int isThrow) {
        System.out.println("test() 方法执行");
        if (isThrow == 1) {
            throw new NullPointerException();
        }
        return "testing";
    }

}
