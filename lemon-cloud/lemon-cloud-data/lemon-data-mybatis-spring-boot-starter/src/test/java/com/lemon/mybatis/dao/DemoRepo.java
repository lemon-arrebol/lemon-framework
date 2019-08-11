package com.lemon.mybatis.dao;

import com.lemon.dynamic.dasource.aspect.AssignDataSource;
import com.lemon.dynamic.dasource.lookup.DynamicRoutingDataSource;
import org.h2.jdbc.JdbcConnection;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author shanhonghao
 * @date 2018-09-04 17:17
 */
@Repository
public class DemoRepo {

    @Resource
    private DynamicRoutingDataSource dynamicDataSource;

    @AssignDataSource("custom02")
    public JdbcConnection connByAnnotation() throws SQLException {
        DataSource dataSource = dynamicDataSource.determineTargetDataSource();
        JdbcConnection conn = (JdbcConnection) dataSource.getConnection().getMetaData().getConnection();
        return conn;
    }

    public JdbcConnection connPrimary() throws SQLException {
        DataSource dataSource = dynamicDataSource.determineTargetDataSource();
        JdbcConnection conn = (JdbcConnection) dataSource.getConnection().getMetaData().getConnection();
        return conn;
    }
}
