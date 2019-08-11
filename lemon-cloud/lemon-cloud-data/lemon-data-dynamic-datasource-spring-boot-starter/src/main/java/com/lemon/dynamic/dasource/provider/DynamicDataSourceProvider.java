package com.lemon.dynamic.dasource.provider;

import java.util.Map;

/**
 * @param
 * @author lemon
 * @description 多数据源加载接口
 * @return
 * @date 2019-08-10 19:44
 */
@FunctionalInterface
public interface DynamicDataSourceProvider {

    /**
     * @return 所有数据源，key为数据源名称
     * @description 加载所有数据源: DataSource, JNDI
     * @author lemon
     * @date 2019-08-10 19:44
     */
    Map<Object, Object> loadDataSources();
}