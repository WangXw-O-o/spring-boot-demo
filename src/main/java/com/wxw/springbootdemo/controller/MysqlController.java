package com.wxw.springbootdemo.controller;

import com.wxw.springbootdemo.core.MysqlTestService;
import com.wxw.springbootdemo.dal.entry.TestDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/mysql")
public class MysqlController {

    @Resource
    private MysqlTestService mysqlTestService;

    @GetMapping("/query/{id}")
    public String testQuery(@PathVariable("id") Long id) {
        TestDO testDO = mysqlTestService.findById(id);
        return testDO.toString();
    }

    @GetMapping("/insert")
    public String testInsert() {
        TestDO testDO = new TestDO();
        testDO.setName("张三");
        testDO.setAge(24);
        mysqlTestService.insertIntoTest(testDO);
        return testDO.toString();
    }

    @GetMapping("/insert/batch")
    public String test() {
        List<TestDO> list = new ArrayList<>();
        TestDO testDO1 = new TestDO();
        testDO1.setName("李四");
        testDO1.setAge(22);
        list.add(testDO1);
        TestDO testDO2 = new TestDO();
        testDO2.setName("李四11111111111111111111111111111111111111111111111111111111111");
        testDO2.setAge(23);
        list.add(testDO2);
        mysqlTestService.batchInsertTestDO(list);
        return "--";
    }

}
