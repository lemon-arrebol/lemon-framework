package com.lemon.dynamic.dasource.provider;

import com.lemon.dynamic.dasource.properties.MultipleDataSourceConfig;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @param
 * @author lemon
 * @description 多数据源加载接口
 * @return
 * @date 2019-08-10 20:26
 */
@Slf4j
public class DefaultDynamicDataSourceProvider extends AbstractDataSourceProvider implements DynamicDataSourceProvider {
    /**
     * 多数据源参数å
     */
    private MultipleDataSourceConfig multipleDataSourceConfig;

    public DefaultDynamicDataSourceProvider(MultipleDataSourceConfig multipleDataSourceConfig) {
        this.multipleDataSourceConfig = multipleDataSourceConfig;
    }

    /**
     * @param
     * @return
     * @description
     * @author lemon
     * @date 2019-08-10 20:21
     */
    @Override
    public Map<Object, Object> loadDataSources() {
        return createDataSourceMap(multipleDataSourceConfig);
    }
}
