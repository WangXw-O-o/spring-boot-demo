package com.wxw.springbootdemo.core;

import com.wxw.springbootdemo.dal.entry.TestDO;

import javax.transaction.Transactional;
import java.util.List;

public interface MysqlTestService {

    TestDO findById(Long id);

    void insertIntoTest(TestDO testDO);

    @Transactional
    void batchInsertTestDO(List<TestDO> list);
}
