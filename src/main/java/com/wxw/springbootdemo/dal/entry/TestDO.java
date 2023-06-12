package com.wxw.springbootdemo.dal.entry;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "test")
public class TestDO implements Serializable {
    private static final long serialVersionUID = 8113307848696430199L;

    @Id
    @GeneratedValue(generator = "TEST_ID")
    @Column(length = 8)
    private Long id;

    @Column(length = 16)
    private String name;
    @Column(length = 4)
    private int age;

}
