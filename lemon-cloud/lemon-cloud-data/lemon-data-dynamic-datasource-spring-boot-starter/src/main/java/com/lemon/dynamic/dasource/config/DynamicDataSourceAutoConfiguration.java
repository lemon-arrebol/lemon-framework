package com.lemon.dynamic.dasource.config;

import com.lemon.cloud.core.util.SpringContextUtil;
import com.lemon.dynamic.dasource.aspect.DynamicDataSourceAnnotationAdvisor;
import com.lemon.dynamic.dasource.lookup.DynamicRoutingDataSource;
import com.lemon.dynamic.dasource.properties.MultipleDataSourceConfig;
import com.lemon.dynamic.dasource.provider.DefaultDynamicDataSourceProvider;
import com.lemon.dynamic.dasource.provider.DynamicDataSourceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * @author lemon
 * @version 1.0
 * @description: 动态路由数据源自动配置
 * @date Create by lemon on 2019-08-10 10:57
 */
@Configuration
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
@EnableConfigurationProperties(MultipleDataSourceConfig.class)
@Import(SpringContextUtil.class)
public class DynamicDataSourceAutoConfiguration {
    @Autowired
    private MultipleDataSourceConfig multipleDataSourceConfig;

    /**
     * @param
     * @return
     * @description 根据配置解析数据源
     * @author lemon
     * @date 2019-08-11 08:11
     */
    @Bean
    @ConditionalOnMissingBean
    public DynamicDataSourceProvider dynamicDataSourceProvider() {
        return new DefaultDynamicDataSourceProvider(this.multipleDataSourceConfig);
    }

    /**
     * @param
     * @return
     * @description 动态切换数据源
     * @author lemon
     * @date 2019-08-11 08:11
     */
    @Bean
    @ConditionalOnMissingBean
    public DataSource dynamicDataSource() {
        DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();
        dynamicRoutingDataSource.setProvider(this.dynamicDataSourceProvider());
        dynamicRoutingDataSource.setStrict(this.multipleDataSourceConfig.isStrict());
        dynamicRoutingDataSource.setLenientFallback(this.multipleDataSourceConfig.isLenientFallback());
        dynamicRoutingDataSource.setPrimary(this.multipleDataSourceConfig.getPrimary());
        return dynamicRoutingDataSource;
    }

    /**
     * @param
     * @return
     * @description 拦截标注切换数据源注解的类或方法，设置数据源标识
     * 必须设置切面顺序大于TransactionAttributeSourceAdvisor(事务切面)的执行顺序，否则事务内数据源切换失效
     * @author lemon
     * @date 2019-08-11 08:11
     */
    @Bean
    @ConditionalOnMissingBean
    public DynamicDataSourceAnnotationAdvisor dynamicDatasourceAnnotationAdvisor() {
        DynamicDataSourceAnnotationAdvisor advisor = new DynamicDataSourceAnnotationAdvisor();
        advisor.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return advisor;
    }

    /**
     * @param
     * @return
     * @description 注入 DataSourceTransactionManager 用于事务管理
     * @author lemon
     * @date 2019-08-10 11:13
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean

    public PlatformTransactionManager transactionManager(DataSource dynamicDataSource) {
        return new DataSourceTransactionManager(dynamicDataSource);
    }
}
