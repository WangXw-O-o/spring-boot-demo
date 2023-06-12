package com.wxw.springbootdemo.dal.dao;

import com.wxw.springbootdemo.dal.entry.TestDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestRepository extends JpaRepository<TestDO, Long> {

    List<TestDO> findAll();


}
