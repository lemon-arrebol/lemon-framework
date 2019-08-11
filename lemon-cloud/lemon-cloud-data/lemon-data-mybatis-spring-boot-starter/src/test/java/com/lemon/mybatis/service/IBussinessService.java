package com.lemon.mybatis.service;

import com.lemon.dynamic.dasource.aspect.AssignDataSource;
import com.lemon.mybatis.aspect.CustomAssignDataSource;
import org.h2.jdbc.JdbcConnection;

import java.sql.SQLException;

/**
 * @author lemon
 * @version 1.0
 * @description: TODO
 * @date Create by lemon on 2019-08-11 15:10
 */
@AssignDataSource("custom02")
public interface IBussinessService {
    void aaa();

    void bbb();

    @AssignDataSource("custom01")
    JdbcConnection connByAnnotation() throws SQLException;

    @CustomAssignDataSource("999")
    JdbcConnection connPrimary() throws SQLException;
}
