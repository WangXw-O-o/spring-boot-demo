package com.wxw.springbootdemo.core.impl;

import com.wxw.springbootdemo.core.MysqlTestService;
import com.wxw.springbootdemo.dal.dao.TestRepository;
import com.wxw.springbootdemo.dal.entry.TestDO;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class MysqlTestServiceImpl implements MysqlTestService {

    @Resource
    private TestRepository testRepository;
    @Resource
    private TransactionTemplate transactionTemplate;

    @Override
    public TestDO findById(Long id) {
        Optional<TestDO> testDO = testRepository.findById(id);
        return testDO.orElse(null);
    }

    @Override
    public void insertIntoTest(TestDO testDO) {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                testRepository.save(testDO);
            }
        });
    }

    @Override
    public void batchInsertTestDO(List<TestDO> list) {
        testRepository.saveAll(list);
    }

}
