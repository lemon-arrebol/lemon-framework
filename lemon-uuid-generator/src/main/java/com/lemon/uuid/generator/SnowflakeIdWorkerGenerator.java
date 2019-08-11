package com.lemon.uuid.generator;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * @author lemon
 * @version 1.0
 * @description: SnowFlake的结构如下(每部分用 - 分开):<br>
 * 0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 - 000000000000 <br>
 * 1位标识，由于long基本类型在Java中是带符号的，最高位是符号位，正数是0，负数是1，所以id一般是正数，最高位是0<br>
 * 41位时间截(毫秒级)，注意，41位时间截不是存储当前时间的时间截，而是存储时间截的差值（当前时间截 - 开始时间截)
 * 得到的值，这里的的开始时间截，一般是我们的id生成器开始使用的时间，由我们程序来指定的（如下下面程序IdWorker类的startTime属性）。41位的时间截，可以使用69年，年T = (1L << 41) / (1000L * 60 * 60 * 24 * 365) = 69<br>
 * 10位的数据机器位，可以部署在1024个节点，包括5位datacenterId和5位workerId<br>
 * 12位序列，毫秒内的计数，12位的计数顺序号支持每个节点每毫秒(同一机器，同一时间截)产生4096个ID序号<br>
 * 加起来刚好64位，为一个Long型。<br>
 * SnowFlake的优点是，整体上按照时间自增排序，并且整个分布式系统内不会产生ID碰撞(由数据中心ID和机器ID作区分)，并且效率较高，经测试，SnowFlake每秒能够产生26万ID左右。
 * @date Create by lemon on 2019-07-20 21:57
 */
@Slf4j
public class SnowflakeIdWorkerGenerator {
    /**
     * @param
     * @return
     * @description
     * @author lemon
     * @date 2019-08-08 16:42
     */
    public static SnowflakeIdWorkerGenerator getInstance() {
        return SnowflakeIdWorkerGenerator.DEFAULT_SNOWFLAKE_ID_WORKER_GENERATOR;
    }

    /**
     * @param
     * @return
     * @description
     * @author lemon
     * @date 2019-08-08 12:20
     */
    public SnowflakeIdWorkerGenerator(long workerId, long datacenterId) {
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException(String.format("Worker Id can't be greater than %d or less than 0", MAX_WORKER_ID));
        }

        if (datacenterId > MAX_DATA_CENTER_ID || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("Datacenter Id can't be greater than %d or less than 0", MAX_DATA_CENTER_ID));
        }

        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    /**
     * @param
     * @return
     * @description 获得下一个ID (该方法是线程安全的)
     * @author lemon
     * @date 2019-08-08 12:11
     */
    public synchronized long nextId() {
        long timestamp = this.getCurrentTimeMillis();

        // 如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        // 如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & SEQUENCE_MASK;
            // 毫秒内序列溢出
            if (sequence == 0) {
                // 阻塞到下一个毫秒,获得新的时间戳
                timestamp = this.getNextMillis(lastTimestamp);
            }
        }
        // 时间戳改变，毫秒内序列重置
        else {
            sequence = 0L;
        }

        // 上次生成ID的时间截
        lastTimestamp = timestamp;

        // 移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - START_TIMESTMP) << TIMESTAMP_SHIFT)
                | (datacenterId << DATA_CENTER_ID_SHIFT)
                | (workerId << WORKER_ID_SHIFT)
                | sequence;
    }

    /**
     * @param
     * @return
     * @description 数据中心ID: 每台机器固定值
     * @author lemon
     * @date 2019-08-08 16:10
     */
    public static long getDatacenterId(long maxDatacenterId) {
        long datacenterId = 0L;

        try {
            // 根据网卡取本机配置的IP 获得网络接口
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();

            if (allNetInterfaces != null && allNetInterfaces.hasMoreElements()) {
                byte[] mac;
                // 声明一个InetAddress类型ip地址
                InetAddress inetAddress;

                // 遍历所有的网络接口
                while (allNetInterfaces.hasMoreElements()) {
                    NetworkInterface netInterface = allNetInterfaces.nextElement();
                    mac = netInterface.getHardwareAddress();

                    if (mac == null) {
                        continue;
                    }

                    datacenterId = ((0x000000FF & datacenterId) | (0x0000FF00 & (long) mac[mac.length - 1])
                            | (0x00FF0000 & (((long) mac[mac.length - 2]) << 8))) >> 6;
                    datacenterId = datacenterId % (maxDatacenterId + 1);
                }
            } else {
                datacenterId = 1L;
            }
        } catch (Exception e) {
            log.error("Failed to get HardwareAddress to generate datacenterId", e);
        }

        log.info("datacenterId is {}", datacenterId);
        return datacenterId;
    }

    /**
     * @param
     * @return
     * @description 工作机器ID: 每个进程不同值
     * @author lemon
     * @date 2019-08-08 16:11
     */
    public static long getWorkerId(long dataCenterId, long maxWorkerId) {
        StringBuffer workerIdSB = new StringBuffer();
        workerIdSB.append(dataCenterId);
        String name = ManagementFactory.getRuntimeMXBean().getName();
        log.info("PID is {}", name);

        if (StringUtils.isNotBlank(name)) {
            // GET jvmPid
            workerIdSB.append(name.split("@")[0]);
        }

        //  MAC + PID 的 hashcode 获取16个低位
        long workerId = (workerIdSB.toString().hashCode() & 0xffff) % (maxWorkerId + 1);
        log.info("workerId is {}", workerId);
        return workerId;
    }

    /**
     * @param lastTimestamp 上次生成ID的时间截
     * @return
     * @description 阻塞到下一个毫秒，直到获得新的时间戳
     * @author lemon
     * @date 2019-08-08 12:07
     */
    protected long getNextMillis(long lastTimestamp) {
        long timestamp = getCurrentTimeMillis();

        while (timestamp <= lastTimestamp) {
            timestamp = getCurrentTimeMillis();
        }

        return timestamp;
    }

    /**
     * @param
     * @return
     * @description 返回以毫秒为单位的当前时间
     * @author lemon
     * @date 2019-08-08 12:07
     */
    protected long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * @param
     * @return
     * @description
     * @author lemon
     * @date 2019-08-08 14:15
     */
    private SnowflakeIdWorkerGenerator() {
        this.datacenterId = SnowflakeIdWorkerGenerator.getDatacenterId(SnowflakeIdWorkerGenerator.MAX_DATA_CENTER_ID);
        this.workerId = SnowflakeIdWorkerGenerator.getWorkerId(this.datacenterId, SnowflakeIdWorkerGenerator.MAX_WORKER_ID);
    }

    /**
     * 默认唯一ID生成器
     */
    private static final SnowflakeIdWorkerGenerator DEFAULT_SNOWFLAKE_ID_WORKER_GENERATOR = new SnowflakeIdWorkerGenerator();

    /**
     * 开始时间截(2019-08-08): 一般是我们的id生成器开始使用的时间，由我们程序来指定的（如下下面程序IdWorker类的startTime属性）
     */
    private static final long START_TIMESTMP;

    /**
     * 机器id所占的位数
     */
    private static final long WORKER_ID_BIT_SIZE = 5L;

    /**
     * 数据标识id所占的位数
     */
    private static final long DATA_CENTER_ID_BIT_SIZE = 5L;

    /**
     * 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
     */
    private static final long MAX_WORKER_ID = -1L ^ (-1L << WORKER_ID_BIT_SIZE);

    /**
     * 支持的最大数据标识id，结果是31
     */
    private static final long MAX_DATA_CENTER_ID = -1L ^ (-1L << DATA_CENTER_ID_BIT_SIZE);

    /**
     * 序列在id中占的位数
     */
    private static final long SEQUENCE_BIT_SIZE = 12L;

    /**
     * 机器ID向左移12位
     */
    private static final long WORKER_ID_SHIFT = SEQUENCE_BIT_SIZE;

    /**
     * 数据标识id向左移17位(12+5)
     */
    private static final long DATA_CENTER_ID_SHIFT = SEQUENCE_BIT_SIZE + WORKER_ID_BIT_SIZE;

    /**
     * 时间截向左移22位(5+5+12)
     */
    private static final long TIMESTAMP_SHIFT = SEQUENCE_BIT_SIZE + WORKER_ID_BIT_SIZE + DATA_CENTER_ID_BIT_SIZE;

    /**
     * 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095)
     */
    private static final long SEQUENCE_MASK = -1L ^ (-1L << SEQUENCE_BIT_SIZE);

    /**
     * 工作机器ID(0~31)
     */
    private long workerId;

    /**
     * 数据中心ID(0~31)
     */
    private long datacenterId;

    /**
     * 毫秒内序列(0~4095)
     */
    private long sequence = 0L;

    /**
     * 上次生成ID的时间截
     */
    private long lastTimestamp = -1L;

    static {
        START_TIMESTMP = 1565238312232L;
    }
}
