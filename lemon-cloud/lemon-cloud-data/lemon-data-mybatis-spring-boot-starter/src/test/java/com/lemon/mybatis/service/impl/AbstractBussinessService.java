package com.lemon.mybatis.service.impl;

import com.lemon.mybatis.aspect.CustomAssignDataSource;
import com.lemon.mybatis.dao.DemoRepo;
import com.lemon.mybatis.service.IBussinessService;
import org.checkerframework.checker.units.qual.A;
import org.h2.jdbc.JdbcConnection;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.SQLException;

/**
 * @author lemon
 * @version 1.0
 * @description: TODO
 * @date Create by lemon on 2019-08-11 15:11
 */
@Service
public class AbstractBussinessService implements IBussinessService {
    @Resource
    private DemoRepo demoRepo;

    @CustomAssignDataSource("6666")
    @Override
    public void aaa() {

    }

    @Transactional
    @Override
    public void bbb() {
        System.out.println("bbb======start");

        try {          this.connByAnnotation();
            AbstractBussinessService aa = (AbstractBussinessService) AopContext.currentProxy();
            System.out.println("bbb======");
            aa.connPrimary();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("bbb======end");
    }

    @Transactional
    @Override
    public JdbcConnection connByAnnotation() throws SQLException {
        return demoRepo.connByAnnotation();
    }

    @Override
    public JdbcConnection connPrimary() throws SQLException {
        return demoRepo.connPrimary();
    }
}
