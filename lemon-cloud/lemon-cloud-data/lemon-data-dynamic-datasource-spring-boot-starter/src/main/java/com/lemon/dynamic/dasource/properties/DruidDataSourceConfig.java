package com.lemon.dynamic.dasource.properties;

import com.alibaba.druid.pool.ExceptionSorter;
import com.google.common.collect.Lists;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * @param
 * @author lemon
 * @description druid配置
 * @return
 * @date 2019-08-10 14:44
 */
@Data
public class DruidDataSourceConfig implements Cloneable {
    /**
     * 配置这个属性的意义在于，如果存在多个数据源，监控的时候可以通过名字来区分开来。
     * 如果没有配置，将会生成一个名字，格式是："DataSource-" + System.identityHashCode(this)
     */
    private String name;

    /**
     * 连接数据库的url，不同数据库不一样
     */
    private String jdbcUrl;

    /**
     * 连接数据库的用户名
     */
    private String username;

    /**
     * 连接数据库的密码。如果你不希望密码直接写在配置文件中，可以使用ConfigFilter。详细看这里：
     * https://github.com/alibaba/druid/wiki/%E4%BD%BF%E7%94%A8ConfigFilter
     */
    private String password;

    /**
     * 根据url自动识别	这一项可配可不配，如果不配置druid会根据url自动识别dbType，
     * 然后选择相应的driverClassName
     */
    private String driverClassName;

    /**
     * 连接出错后再尝试连接三次
     */
    private Integer connectionErrorRetryAttempts = 3;

    /**
     * 数据库服务宕机自动重连机制
     * 启用数据库服务断线自动重连机制：保障数据库服务修复后系统自动重连，无需重新启动应用服务；
     */
    private Boolean breakAfterAcquireFailure = true;

    /**
     * 连接出错后重试时间间隔
     */
    private Long timeBetweenConnectErrorMillis = 5 * 60 * 1000L;

    /**
     * 异步初始化策略
     */
    private Boolean asyncInit = true;

    /**
     * 初始化时建立物理连接的个数。初始化发生在显示调用init方法，或者第一次getConnection时
     */
    private Integer initialSize = 3;

    /**
     * 最大连接池数量
     */
    private Integer maxActive = 8;

    /**
     * 已经不再使用，配置了也没效果
     */
    @Deprecated
    private Integer maxIdle = 8;

    /**
     * 最小连接池数量
     */
    private Integer minIdle = 0;

    /**
     * 获取连接时最大等待时间，单位毫秒。配置了maxWait之后，缺省启用公平锁，并发效率会有所下降，如果需要可以通过配置useUnfairLock属性为true使用非公平锁。
     */
    private Integer maxWait = -1;

    /**
     * 是否缓存preparedStatement，也就是PSCache。PSCache对支持游标的数据库性能提升巨大，比如说oracle。在mysql下建议关闭。
     */
    private Boolean poolPreparedStatements = false;

    /**
     *
     */
    private Boolean sharePreparedStatements = false;

    /**
     *
     */
    private Integer maxPoolPreparedStatementPerConnectionSize = 20;

    /**
     * 要启用PSCache，必须配置大于0，当大于0时，poolPreparedStatements自动触发修改为true。在Druid中，不会存在Oracle下PSCache占用内存过多的问题，可以把这个数值配置大一些，比如说100
     */
    private Integer maxOpenPreparedStatements = -1;

    /**
     * 用来检测连接是否有效的sql，要求是一个查询语句。如果validationQuery为null，testOnBorrow、testOnReturn、testWhileIdle都不会其作用。
     */
    private String validationQuery = "select 1";

    /**
     * 申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。
     */
    private Boolean testOnBorrow = false;

    /**
     * 归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能
     */
    private Boolean testOnReturn = false;

    /**
     * 建议配置为true，不影响性能，并且保证安全性。申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
     */
    private Boolean testWhileIdle = true;

    /**
     * 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
     * 有两个含义：1、 Destroy线程会检测连接的间隔时间。2、 testWhileIdle的判断依据，详细看testWhileIdle属性的说明
     */
    private Long timeBetweenEvictionRunsMillis = 60 * 1000L;

    /**
     * 配置一个连接在池中最小生存的时间，单位是毫秒
     */
    private Long minEvictableIdleTimeMillis = 30 * 60 * 1000L;

    /**
     * 配置一个连接在池中最大生存的时间，单位是毫秒
     */
    private Long maxEvictableIdleTimeMillis = 7 * 60L * 60L * 1000L;

    /**
     * 不再使用，一个DruidDataSource只支持一个EvictionRun
     */
    private Integer numTestsPerEvictionRun;

    /**
     * 物理连接初始化的时候执行的sql
     */
    private List<String> connectionInitSqls;

    /**
     * 根据dbType自动识别	当数据库抛出一些不可恢复的异常时，抛弃连接
     * com.alibaba.druid.pool.ExceptionSorter
     */
    private ExceptionSorter exceptionSorter;

    private String exceptionSorterClassName;

    /**
     * 配置监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙
     * 属性类型是字符串，通过别名的方式配置扩展插件，常用的插件有：监控统计用的filter，stat日志用的filter，log4j防御sql注入的filter，wall
     * List<com.alibaba.druid.filter.Filter>
     */
    private List<String> filters;

    /**
     * 类型是List<com.alibaba.druid.filter.Filter>，如果同时配置了filters和proxyFilters，是组合关系，并非替换关系
     */
    private List<com.alibaba.druid.filter.Filter> proxyFilters = Lists.newArrayList();

    private List<String> proxyFilterBeanNames;

    /**
     *
     */
    private Integer validationQueryTimeout = -1;

    /**
     * 通过connectProperties属性来打开mergeSql功能；慢SQL记录
     */
    private String connectionProperties = "druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000";

    /**
     *
     */
    private String webStatFilterExclusions = "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*";

    /**
     *
     */
    private Boolean removeAbandoned = false;

    /**
     *
     */
    private Long removeAbandonedTimeoutMillis = 5 * 60 * 1000L;

    /**
     * 合并多个DruidDataSource的监控数据
     */
    private Boolean useGlobalDataSourceStat = false;

    /**
     * @param
     * @return
     * @description
     * @author lemon
     * @date 2019-08-10 20:17
     */
    @Override
    public DruidDataSourceConfig clone() throws CloneNotSupportedException {
        DruidDataSourceConfig druidDataSourceConfig = (DruidDataSourceConfig) super.clone();

        if (CollectionUtils.isNotEmpty(this.connectionInitSqls)) {
            druidDataSourceConfig.setConnectionInitSqls(Lists.newArrayList(this.connectionInitSqls));
        }

        if (CollectionUtils.isNotEmpty(this.filters)) {
            druidDataSourceConfig.setFilters(Lists.newArrayList(this.filters));
        }

        if (CollectionUtils.isNotEmpty(this.proxyFilterBeanNames)) {
            druidDataSourceConfig.setProxyFilterBeanNames(Lists.newArrayList(this.proxyFilterBeanNames));
        }

        return druidDataSourceConfig;
    }
}