package com.lemon.dynamic.dasource.builder;

import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.lemon.cloud.core.util.BeanUtil;
import com.lemon.cloud.core.util.SpringContextUtil;
import com.lemon.dynamic.dasource.properties.SingleDataSourceConfig;
import com.lemon.dynamic.dasource.properties.DruidDataSourceConfig;
import com.lemon.dynamic.dasource.properties.MultipleDataSourceConfig;
import com.lemon.dynamic.dasource.properties.HikariDataSourceConfig;
import com.lemon.dynamic.dasource.consatnts.Constants;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

/**
 * @author lemon
 * @version 1.0
 * @description: TODO
 * @date Create by lemon on 2019-08-10 11:32
 */
@Slf4j
public class DataSourceBuilder {
    /**
     * @param
     * @return
     * @description
     * @author lemon
     * @date 2019-08-10 15:38
     */
    public static Map<Object, Object> buildDataSources(MultipleDataSourceConfig multipleDataSourceConfig) {
        Map<String, SingleDataSourceConfig> dataSourcePropertiesMap = multipleDataSourceConfig.getDatasources();
        Map<Object, Object> dataSourceMap = Maps.newHashMapWithExpectedSize(dataSourcePropertiesMap.size());

        dataSourcePropertiesMap.forEach((key, singleDataSourceConfig) -> {
            // 合并druid公共配置
            if (multipleDataSourceConfig.getDruid() != null) {
                try {
                    DruidDataSourceConfig druid = multipleDataSourceConfig.getDruid().clone();
                    BeanUtil.copyPropertiesIgnoreNull(singleDataSourceConfig.getDruid(), druid);
                    multipleDataSourceConfig.setDruid(druid);
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }

            // 合并hikari公共配置
            if (multipleDataSourceConfig.getHikari() != null) {
                try {
                    HikariDataSourceConfig hikari = multipleDataSourceConfig.getHikari().clone();
                    BeanUtil.copyPropertiesIgnoreNull(singleDataSourceConfig.getHikari(), hikari);
                    multipleDataSourceConfig.setHikari(hikari);
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }

            dataSourceMap.put(key, DataSourceBuilder.buildDataSource(singleDataSourceConfig));
        });

        return dataSourceMap;
    }

    /**
     * @param
     * @return
     * @description
     * @author lemon
     * @date 2019-08-10 15:38
     */
    public static DataSource buildDataSource(SingleDataSourceConfig singleDataSourceConfig) {
        String type = singleDataSourceConfig.getType();

        if (StringUtils.isBlank(type)) {
            try {
                Class.forName(Constants.HIKARICP_DATASOURCE_CLASS_NAME);
                return createHikariDataSource(singleDataSourceConfig);
            } catch (ClassNotFoundException e) {
                log.warn(String.format("will not use connection pool, because default hikari dependency not exists"), e);
            }
        } else if (Constants.DRUID_DATASOURCE_CLASS_NAME.equals(type)) {
            try {
                // 在指定 druid 链接池时, 检查依赖必须要存在
                Class.forName(Constants.DRUID_DATASOURCE_CLASS_NAME);
            } catch (ClassNotFoundException e) {
                log.error(String.format("want to use druid but failed, because druid dependency not exists"), e);
                ReflectionUtils.rethrowRuntimeException(e);
            }

            return createDruidDataSource(singleDataSourceConfig);
        } else if (Constants.HIKARICP_DATASOURCE_CLASS_NAME.equals(type)) {
            try {
                // 在指定 hikari 链接池时, 检查依赖必须要存在
                Class.forName(Constants.HIKARICP_DATASOURCE_CLASS_NAME);
            } catch (ClassNotFoundException e) {
                log.error(String.format("want to use hikari but failed, because hikari dependency not exists"), e);
                ReflectionUtils.rethrowRuntimeException(e);
            }

            return createHikariDataSource(singleDataSourceConfig);
        }

        // 不配置连接池
        return createBasicDataSource(singleDataSourceConfig);
    }

    /**
     * @param
     * @return
     * @description
     * @author lemon
     * @date 2019-08-10 15:38
     */
    private static DataSource createDruidDataSource(SingleDataSourceConfig singleDataSourceConfig) {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(singleDataSourceConfig.getJdbcUrl());
        druidDataSource.setUsername(singleDataSourceConfig.getUsername());
        druidDataSource.setPassword(singleDataSourceConfig.getPassword());
        druidDataSource.setDriverClassName(singleDataSourceConfig.getDriverClassName());

        DruidDataSourceConfig druidProperties = singleDataSourceConfig.getDruid();

        if (druidProperties != null) {
            if (StringUtils.isNotBlank(druidProperties.getExceptionSorterClassName())) {
                Class<com.alibaba.druid.pool.ExceptionSorter> exceptionSorterClass = findType(druidProperties.getExceptionSorterClassName());
                druidProperties
                        .setExceptionSorter(BeanUtils.
                                instantiateClass(exceptionSorterClass));
            }

            if (CollectionUtils.isNotEmpty(druidProperties.getProxyFilterBeanNames())) {
                druidProperties.getProxyFilterBeanNames().forEach((proxyFilterBeanName) -> {
                    druidProperties
                            .getProxyFilters()
                            .add(SpringContextUtil
                                    .getBean(proxyFilterBeanName, com.alibaba.druid.filter.Filter.class));
                });
            }

            try {
                BeanUtil.copyPropertiesIgnoreNull(druidProperties, druidDataSource);
                druidDataSource.init();
            } catch (SQLException e) {
                log.error("create druid datasource failed", e);
            }
        }

        return druidDataSource;
    }

    /**
     * @param
     * @return
     * @description
     * @author lemon
     * @date 2019-08-10 15:38
     */
    private static DataSource createHikariDataSource(SingleDataSourceConfig singleDataSourceConfig) {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(singleDataSourceConfig.getJdbcUrl());
        hikariDataSource.setUsername(singleDataSourceConfig.getUsername());
        hikariDataSource.setPassword(singleDataSourceConfig.getPassword());
        hikariDataSource.setDriverClassName(singleDataSourceConfig.getDriverClassName());

        HikariDataSourceConfig hikariProperties = singleDataSourceConfig.getHikari();

        if (hikariProperties != null) {
            if (StringUtils.isNotBlank(hikariProperties.getThreadFactoryBeanName())) {
                hikariProperties
                        .setThreadFactory(SpringContextUtil
                                .getBean(hikariProperties
                                        .getThreadFactoryBeanName(), ThreadFactory.class));
            }

            if (StringUtils.isNotBlank(hikariProperties.getScheduledExecutorBeanName())) {
                hikariProperties
                        .setScheduledExecutor(SpringContextUtil
                                .getBean(hikariProperties
                                        .getScheduledExecutorBeanName(), ScheduledExecutorService.class));
            }

            try {
                BeanUtil.copyPropertiesIgnoreNull(hikariProperties, hikariDataSource);
            } catch (Exception e) {
                log.error("create hikari datasource failed", e);
            }
        }

        return hikariDataSource;
    }

    /**
     * @param
     * @return
     * @description
     * @author lemon
     * @date 2019-08-10 15:36
     */
    private static DataSource createBasicDataSource(SingleDataSourceConfig singleDataSourceConfig) {
        Preconditions.checkArgument(StringUtils.isNotBlank(singleDataSourceConfig.getType()), "");

        try {
            Class<? extends DataSource> type = DataSourceBuilder.findType(singleDataSourceConfig.getType());

            org.springframework.boot.jdbc.DataSourceBuilder
                    .create()
                    .type(type)
                    .driverClassName(singleDataSourceConfig.getDriverClassName())
                    .url(singleDataSourceConfig.getJdbcUrl())
                    .username(singleDataSourceConfig.getUsername())
                    .password(singleDataSourceConfig.getPassword())
                    .build();
        } catch (Exception e) {
            log.error("create basic datasource failed", e);
        }

        return null;
    }

    /**
     * @param
     * @return 不存在返回NULL
     * @description 获取数据源
     * @author lemon
     * @date 2019-08-10 15:28
     */
    public static <T> T findType(String className) {
        return DataSourceBuilder.findType(null, className);
    }

    /**
     * @param
     * @return 不存在返回NULL
     * @description 获取数据源
     * @author lemon
     * @date 2019-08-10 15:28
     */
    public static <T> T findType(ClassLoader classLoader, String className) {
        try {
            return (T) ClassUtils.forName(className, classLoader);
        } catch (Exception e) {
            log.error(String.format("Load class [%s] failed", className), e);
        }

        return null;
    }
}
