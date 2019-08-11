package com.lemon.mybatis;

import com.alibaba.fastjson.JSON;
import com.lemon.dynamic.dasource.properties.MultipleDataSourceConfig;
import com.lemon.dynamic.dasource.properties.SingleDataSourceConfig;
import com.lemon.mybatis.dao.DemoRepo;
import com.lemon.mybatis.service.impl.AbstractBussinessService;
import org.h2.jdbc.JdbcConnection;
import org.testng.annotations.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author shanhonghao
 * @date 2018-09-04 17:08
 */
public class DynamicDataSourceTest extends ApplicationTests {

    @Autowired
    private MultipleDataSourceConfig multipleDataSourceConfig;

    @Resource
    private AbstractBussinessService abstractBussinessService;

    @Test
    public void test() {
        // 事务内切换数据源
        abstractBussinessService.bbb();
    }

    @Test()
    public void testDatasourceLoad() {
        Map<String, SingleDataSourceConfig> datasource = multipleDataSourceConfig.getDatasources();

        datasource.forEach((key, value) -> {
            System.out.println(key + "===" + JSON.toJSONString(value));
        });
    }

    @Test
    public void testSwitchByAnnotation() throws SQLException {
        JdbcConnection conn = abstractBussinessService.connByAnnotation();
        System.out.println(conn.getMetaData().getUserName().toLowerCase());
    }

    @Test
    public void testSwitchPrimary() throws SQLException {
        JdbcConnection conn = abstractBussinessService.connPrimary();
        System.out.println(conn.getMetaData().getUserName().toLowerCase());
    }
}
