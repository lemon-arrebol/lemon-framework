package com.lemon.dynamic.dasource.lookup;

import com.google.common.collect.Maps;
import com.lemon.dynamic.dasource.consatnts.Constants;
import com.lemon.dynamic.dasource.holder.DynamicDataSourceContextHolder;
import com.lemon.dynamic.dasource.provider.DynamicDataSourceProvider;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author lemon
 * @version 1.0
 * @description: 动态路由数据源
 * @date Create by lemon on 2019-08-10 10:10
 */
@Slf4j
public class DynamicRoutingDataSource extends AbstractRoutingDataSource implements DisposableBean {
    /**
     * 是否使用严格模式，数据源不存在抛异常
     */
    @Setter
    private boolean strict;

    /**
     * 是否使用默认数据源
     */
    @Setter
    private boolean lenientFallback = true;

    /**
     * 默认数据源名称，默认master
     */
    @Setter
    private String primary;

    /**
     * 原始数据源: DataSource, JNDI
     */
    private final Map<Object, Object> targetDataSources = Maps.newConcurrentMap();

    /**
     * 解析后的数据源: DataSource
     */
    private final Map<Object, DataSource> resolvedDataSources = Maps.newConcurrentMap();

    /**
     * 默认数据源
     */
    private DataSource resolvedDefaultDataSource;

    @Setter
    private DynamicDataSourceProvider provider;

    /**
     * @param
     * @return
     * @description 添加数据源
     * @author lemon
     * @date 2019-08-10 10:32
     */
    public void addDataSource(Object key, Object value) {
        Object lookupKey = super.resolveSpecifiedLookupKey(key);
        DataSource dataSource = super.resolveSpecifiedDataSource(value);
        this.resolvedDataSources.put(lookupKey, dataSource);
        // 将数据源的 key 放到数据源上下文的 key 集合中，用于切换时判断数据源是否有效
        DynamicDataSourceContextHolder.addDataSourceKey(lookupKey);

        if (log.isDebugEnabled()) {
            log.debug("DataSource for original [{}] lookupKey [{}] added successfully", key, lookupKey);
        }
    }

    /**
     * @param
     * @return
     * @description 添加多个数据源
     * @author lemon
     * @date 2019-08-10 10:32
     */
    public void addDataSources(Map<Object, Object> dataSources) {
        if (MapUtils.isEmpty(dataSources)) {
            return;
        }

        this.targetDataSources.putAll(dataSources);

        dataSources.forEach((key, value) -> {
            this.addDataSource(key, value);
        });

        if (log.isDebugEnabled()) {
            log.debug("Successfully added [{}] data sources", dataSources.size());
        }
    }

    /**
     * @param
     * @return
     * @description 如果希望所有数据源在启动配置时就加载好，这里通过设置数据源Key值来切换数据，定制这个方法
     * @author lemon
     * @date 2019-08-10 10:32
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceContextHolder.peekDataSourceKey();
    }

    /**
     * @param
     * @return
     * @description 如果不希望数据源在启动配置时就加载好，可以定制这个方法，从任何你希望的地方读取并返回数据源
     * 比如从数据库、文件、外部接口等读取数据源信息，并最终返回一个DataSource实现类对象即可
     * @author lemon
     * @date 2019-08-10 10:32
     */
    @Override
    public DataSource determineTargetDataSource() {
        Object lookupKey = this.determineCurrentLookupKey();

        // 没有指定数据源则取默认数据源
        if (!DynamicDataSourceContextHolder.containDataSourceKey(lookupKey)) {
            DynamicDataSourceContextHolder.pushDataSourceKey(this.primary);

            if (log.isDebugEnabled()) {
                log.debug("The current data source [{}] does not exist, switched to the default dataSource [{}]", lookupKey, this.primary);
            }
        }

        DataSource dataSource = this.resolvedDataSources.get(lookupKey);

        if (dataSource == null && (this.lenientFallback || lookupKey == null)) {
            dataSource = this.resolvedDefaultDataSource;
        }

        if (strict) {
            throw new IllegalStateException(String.format("Cannot determine target dataSource for lookup key [%s], no default dataSource", lookupKey));
        }

        return dataSource;
    }

    /**
     * @param
     * @return
     * @description 初始化数据源
     * @author lemon
     * @date 2019-08-10 21:37
     */
    @Override
    public void afterPropertiesSet() {
        if (StringUtils.isBlank(primary)) {
            this.primary = Constants.DEFAULT_DATA_SOURCE;
        }

        // 数据源 JNDI，调用super.resolveSpecifiedDataSource方法会用到
        super.setDataSourceLookup(null);
        this.addDataSources(this.provider.loadDataSources());

        if (this.resolvedDataSources.get(this.primary) == null) {
            throw new IllegalStateException(String.format("Cannot default dataSource for lookup key [%s] noexist", this.primary));
        }

        this.resolvedDefaultDataSource = this.resolvedDataSources.get(this.primary);
    }

    /**
     * @param
     * @return
     * @description 关闭数据源
     * @author lemon
     * @date 2019-08-10 21:06
     */
    @Override
    public void destroy() throws Exception {
        log.info("Dynamic datasource start closing ....");

        try {
            this.resolvedDataSources.forEach((lookupKey, dataSource) -> {
                Class<? extends DataSource> clazz = dataSource.getClass();

                if (clazz == null) {
                    log.warn("Dynamic datasource for [{}] no exist", lookupKey);
                    return;
                }

                try {
                    Method closeMethod = clazz.getDeclaredMethod("close");
                    closeMethod.invoke(dataSource);
                } catch (Exception e) {
                    log.warn("Dynamic datasource close the datasource named [{}] failed,", lookupKey);
                }
            });
        } finally {
            DynamicDataSourceContextHolder.clearDataSourceKeys();
            DynamicDataSourceContextHolder.clearDataSourceKey();
        }

        log.info("Dynamic datasource all closed success");
    }
}
