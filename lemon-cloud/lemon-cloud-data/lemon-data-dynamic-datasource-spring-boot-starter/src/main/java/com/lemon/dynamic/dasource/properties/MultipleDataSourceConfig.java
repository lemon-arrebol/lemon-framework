package com.lemon.dynamic.dasource.properties;

import com.google.common.collect.Maps;
import com.lemon.dynamic.dasource.consatnts.Constants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * @author lemon
 * @version 1.0
 * @description: 多数据源配置信息
 * @date Create by lemon on 2019-08-10 10:59
 */
@Setter
@Getter
@ConfigurationProperties(prefix = Constants.DATA_SOURCE_CONFIG_PREFIX, ignoreUnknownFields = false)
public class MultipleDataSourceConfig {
    /**
     * 是否使用严格模式，数据源不存在抛异常
     */
    private boolean strict;

    /**
     * 是否使用默认数据源
     */
    private boolean lenientFallback = true;

    /**
     * 默认数据源标识
     */
    private String primary = Constants.DEFAULT_DATA_SOURCE;

    /**
     * 公共连接池配置: druid
     */
    private DruidDataSourceConfig druid;

    /**
     * 公共连接池配置: hikari
     */
    private HikariDataSourceConfig hikari;

    /**
     * 数据源配置
     */
    private Map<String, SingleDataSourceConfig> datasources = Maps.newLinkedHashMap();
}
