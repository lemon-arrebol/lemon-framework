package com.lemon.uuid.generator;

/**
 * @author lemon
 * @version 1.0
 * @description: TODO
 * @date Create by lemon on 2019-07-20 21:57
 */
public class DefaultKeyGenerator {
    private static final long START_STMP = 1504195200000L;
    private static final long SEQUENCE_BIT = 12L;
    private static final long MACHINE_BIT = 5L;
    private static final long DATACENTER_BIT = 5L;
    private static final long MAX_DATACENTER_NUM = 31L;
    private static final long MAX_MACHINE_NUM = 31L;
    private static final long MAX_SEQUENCE = 4095L;
    private static final long MACHINE_LEFT = 12L;
    private static final long DATACENTER_LEFT = 17L;
    private static final long TIMESTMP_LEFT = 22L;
    private long datacenterId;
    private long machineId;
    private long sequence = 0L;
    private long lastStmp = -1L;

    public DefaultKeyGenerator(long datacenterId, long machineId) {
        if (datacenterId <= 31L && datacenterId >= 0L) {
            if (machineId <= 31L && machineId >= 0L) {
                this.datacenterId = datacenterId;
                this.machineId = machineId;
            } else {
                throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
            }
        } else {
            throw new IllegalArgumentException("datacenterId can't be greater than MAX_DATACENTER_NUM or less than 0");
        }
    }

    public DefaultKeyGenerator() {
        this.datacenterId = 7L;
        this.machineId = 7L;
    }

    public synchronized long generateKey() {
        long currStmp = this.getNewstmp();

        if (currStmp < this.lastStmp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        } else {
            if (currStmp == this.lastStmp) {
                this.sequence = this.sequence + 1L & 4095L;
                if (this.sequence == 0L) {
                    currStmp = this.getNextMill();
                }
            } else {
                this.sequence = 0L;
            }

            this.lastStmp = currStmp;
            return currStmp - 1504195200000L << 22 | this.datacenterId << 17 | this.machineId << 12 | this.sequence;
        }
    }

    private long getNextMill() {
        long mill;

        for (mill = this.getNewstmp(); mill <= this.lastStmp; mill = this.getNewstmp()) {
        }

        return mill;
    }

    private long getNewstmp() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) {
    }

    public void setWorkerId(long workerId) {
        this.machineId = workerId;
    }

    public void setDatacenterId(long datacenterId) {
        this.datacenterId = datacenterId;
    }
}
