package com.lemon.dynamic.dasource.properties;

import lombok.Data;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @author lemon
 * @version 1.0
 * @description: 单个数据源配置
 * @date Create by lemon on 2019-08-10 11:02
 */
@Data
public class SingleDataSourceConfig {
    /**
     * JDBC type,如果不设置自动查找 HikariCp Druid
     */
    private String type;

    /**
     * JDBC driver
     */
    private String driverClassName;

    /**
     * JDBC url 地址
     */
    private String jdbcUrl;

    /**
     * JDBC 用户名
     */
    private String username;

    /**
     * JDBC 密码
     */
    private String password;

    @NestedConfigurationProperty
    private DruidDataSourceConfig druid;

    @NestedConfigurationProperty
    private HikariDataSourceConfig hikari;
}
