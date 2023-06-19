package com.wxw.springbootdemo.entry;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class People {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private Long age;
    private String address;

}
