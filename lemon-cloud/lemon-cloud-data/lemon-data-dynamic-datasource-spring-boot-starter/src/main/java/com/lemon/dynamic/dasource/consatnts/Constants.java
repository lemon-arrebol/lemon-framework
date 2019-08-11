package com.lemon.dynamic.dasource.consatnts;

/**
 * @author lemon
 * @version 1.0
 * @description: TODO
 * @date Create by lemon on 2019-08-10 11:17
 */
public class Constants {
    /**
     * 数据源前缀
     */
    public static final String DATA_SOURCE_CONFIG_PREFIX = "spring.datasource.dynamic";

    /**
     * 默认数据源
     */
    public static final String DEFAULT_DATA_SOURCE = "master";

    /**
     * druid数据库连接池
     */
    public static final String DRUID_DATASOURCE_CLASS_NAME = "com.alibaba.druid.pool.DruidDataSource";

    /**
     * hikari数据库连接池
     */
    public static final String HIKARICP_DATASOURCE_CLASS_NAME = "com.zaxxer.hikari.HikariDataSource";
}
