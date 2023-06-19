package com.wxw.springbootdemo.controller;

import com.wxw.springbootdemo.entry.People;
import com.wxw.springbootdemo.mapper.PeopleMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;

@RestController
public class MybatisPlusController {

    @Resource
    private PeopleMapper peopleMapper;

    @GetMapping("select/all")
    public List<People> selectAll() {
        return peopleMapper.selectList(null);
    }

    @GetMapping("insert/{count}")
    public void insertByCount(@PathVariable("count") Long count) {
        for (int i = 0; i < count.intValue(); i++) {
            People people = new People();
            people.setName("name-"+i);
            people.setAge(Long.parseLong(String.valueOf((int) (Math.random() * 100))));
            people.setAddress("address-"+i);
            peopleMapper.insert(people);
        }
    }
}
