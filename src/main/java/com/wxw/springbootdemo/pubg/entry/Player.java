package com.wxw.springbootdemo.pubg.entry;

import lombok.Data;

/**
 * 选手信息实体
 */
@Data
public class Player {

    //姓名
    private String name;
    //年龄
    private int age;
    //击杀数
    private int killCount;
    //死亡数
    private int deathsCount;

}
