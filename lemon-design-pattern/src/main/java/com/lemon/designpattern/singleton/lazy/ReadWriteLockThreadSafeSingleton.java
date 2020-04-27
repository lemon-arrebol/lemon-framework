package com.lemon.designpattern.singleton.lazy;

import java.util.Calendar;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author lemon
 * @version 1.0
 * @description: 可重入读写锁实现高效的线程安全的单例模式
 * @date Create by lemon on 2019-11-08 16:57
 */
public class ReadWriteLockThreadSafeSingleton {
    private ReadWriteLockThreadSafeSingleton() {

    }

    private static final ReadWriteLock READ_WRITEL_OCK = new ReentrantReadWriteLock();
    private static final Lock READ_LOCK = READ_WRITEL_OCK.readLock();
    private static final Lock WRITE_LOCK = READ_WRITEL_OCK.writeLock();


    private static volatile ReadWriteLockThreadSafeSingleton singleton;

    public static ReadWriteLockThreadSafeSingleton getInstance() {
        if (singleton == null) {
            READ_LOCK.lock();

            if (singleton == null) {
                // 在获取写锁前必须释放读锁
                READ_LOCK.unlock();
                WRITE_LOCK.lock();
                System.out.println(Thread.currentThread().getName() + " writeLock " + Calendar.getInstance().getTimeInMillis());

                try {
                    if (singleton == null) {
                        System.out.println(Thread.currentThread().getName() + "-" + Calendar.getInstance().getTimeInMillis());

                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        singleton = new ReadWriteLockThreadSafeSingleton();
                    }

                    // 在释放写锁之前通过获取读锁来降级
                    READ_LOCK.lock();
                } catch (Throwable t) {
                    // 可能会抛异常需要捕获
                    READ_LOCK.unlock();
                    throw t;
                } finally {
                    // 释放写锁，保持读锁
                    WRITE_LOCK.unlock();
                }
            }

            try {
                return singleton;
            } finally {
                READ_LOCK.unlock();
            }
        }

        return singleton;
    }
}
