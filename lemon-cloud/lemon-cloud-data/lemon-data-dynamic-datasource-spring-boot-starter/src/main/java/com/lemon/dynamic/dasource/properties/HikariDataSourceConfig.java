package com.lemon.dynamic.dasource.properties;

import lombok.Data;

import javax.sql.DataSource;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

/**
 * @param
 * @author lemon
 * @description hikari配置
 * @return
 * @date 2019-08-10 12:03
 */
@Data
public class HikariDataSourceConfig implements Cloneable {
    /**
     * 控制从池返回的连接的默认自动提交行为。默认值：true
     */
    private Boolean autoCommit = true;
    /**
     * 客户端等待连接的最大毫秒数。如果在没有可用连接的情况下超过此时间，如果小于250毫秒，则被重置回30秒，则会抛出SQLException。默认值：30000
     */
    private Integer connectionTimeout = 30000;
    /**
     * 此属性控制允许连接在池中闲置的最长时间。此设置仅适用于minimumIdle定义为小于maximumPoolSize。
     * 如果idleTimeout+1秒>maxLifetime 且 maxLifetime>0，则会被重置为0（代表永远不会退出）；如果idleTimeout!=0且小于10秒，则会被重置为10秒。默认值：600000（10分钟）
     */
    private Long idleTimeout = 10 * 60 * 1000L;
    /**
     * 此属性控制池中连接的最大生存期，值0表示无限生命周期，如果不等于0且小于30秒则会被重置回30分钟。默认值：1800000（30分钟）
     */
    private Long maxLifetime = 30 * 60 * 1000L;
    /**
     * 该属性控制HikariCP尝试在池中维护的最小空闲连接数，minIdle<0或者minIdle>maxPoolSize,则被重置为maxPoolSize。默认值：与maximumPoolSize相同
     */
    private Integer minimumIdle = 10;
    /**
     * 此属性控制池允许达到的最大大小，包括空闲和正在使用的连接。默认值：10
     * 如果maxPoolSize小于1，则会被重置。当minIdle<=0被重置为DEFAULT_POOL_SIZE则为10;如果minIdle>0则重置为minIdle的值
     */
    private Integer maximumPoolSize = 10;

    /**
     * 连接池的用户定义名称，主要出现在日志记录和JMX管理控制台中以识别池和池配置
     */
    private String poolName = "HikariPool-1-";

    /**
     * 如果池无法成功初始化连接，则此属性控制池是否将 fail fast
     */
    private Long initializationFailTimeout = 1L;

    /**
     * 是否在其自己的事务中隔离内部池查询，例如连接活动测试
     */
    private Boolean isolateInternalQueries = false;

    /**
     * 控制池是否可以通过JMX暂停和恢复
     */
    private Boolean allowPoolSuspension = false;

    /**
     * 从池中获取的连接是否默认处于只读模式
     */
    private Boolean readOnly = false;

    /**
     * 是否注册JMX管理Bean（MBeans）
     */
    private Boolean registerMbeans = false;

    /**
     * 为支持 catalog 概念的数据库设置默认 catalog	driver
     */
    private String catalog;

    /**
     * 该属性设置一个SQL语句，在将每个新连接创建后，将其添加到池中之前执行该语句。
     */
    private String connectionInitSql;

    /**
     * HikariCP将尝试通过仅基于jdbcUrl的DriverManager解析驱动程序，但对于一些较旧的驱动程序，还必须指定driverClassName
     */
    private String driverClassName;

    /**
     * 控制从池返回的连接的默认事务隔离级别
     */
    private String transactionIsolation;

    /**
     * 连接将被测试活动的最大时间量, 如果小于250毫秒，则会被重置回5秒
     */
    private Long validationTimeout = 5000L;

    /**
     * 记录消息之前连接可能离开池的时间量，表示可能的连接泄漏,
     */
    private Long leakDetectionThreshold;

    /**
     * 这个属性允许你直接设置数据源的实例被池包装，而不是让HikariCP通过反射来构造它
     */
    private DataSource dataSource;

    private String dataSourceBeanName;

    /**
     * 该属性为支持模式概念的数据库设置默认模式
     */
    private String schema;

    /**
     * 此属性允许您设置将用于创建池使用的所有线程的java.util.concurrent.ThreadFactory的实例
     */
    private ThreadFactory threadFactory;

    private String threadFactoryBeanName;

    /**
     * 此属性允许您设置将用于各种内部计划任务的java.util.concurrent.ScheduledExecutorService实例
     */
    private ScheduledExecutorService scheduledExecutor;

    private String scheduledExecutorBeanName;

    /**
     * @param
     * @return
     * @description
     * @author lemon
     * @date 2019-08-10 20:17
     */
    @Override
    public HikariDataSourceConfig clone() throws CloneNotSupportedException {
        HikariDataSourceConfig hikariDataSourceConfig = (HikariDataSourceConfig) super.clone();
        return hikariDataSourceConfig;
    }
}