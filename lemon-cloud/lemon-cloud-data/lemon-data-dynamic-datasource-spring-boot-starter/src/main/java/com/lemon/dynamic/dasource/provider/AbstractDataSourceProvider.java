package com.lemon.dynamic.dasource.provider;

import com.lemon.dynamic.dasource.builder.DataSourceBuilder;
import com.lemon.dynamic.dasource.properties.MultipleDataSourceConfig;
import com.lemon.dynamic.dasource.properties.SingleDataSourceConfig;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @param
 * @author lemon
 * @description 多数据源加载接口
 * @return
 * @date 2019-08-10 20:26
 */
@Slf4j
public abstract class AbstractDataSourceProvider implements DynamicDataSourceProvider {
    /**
     * @param
     * @return
     * @description
     * @author lemon
     * @date 2019-08-10 20:20
     */
    protected Map<Object, Object> createDataSourceMap(MultipleDataSourceConfig multipleDataSourceConfig) {
        return DataSourceBuilder.buildDataSources(multipleDataSourceConfig);
    }
}