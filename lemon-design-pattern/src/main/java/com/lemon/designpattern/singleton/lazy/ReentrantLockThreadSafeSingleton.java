package com.lemon.designpattern.singleton.lazy;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lemon
 * @version 1.0
 * @description: 可重入锁高效的线程安全的单例模式实现类
 * @date Create by lemon on 2019-11-08 16:57
 */
public class ReentrantLockThreadSafeSingleton {
    private ReentrantLockThreadSafeSingleton() {

    }

    private static final Lock REENTRANT_LOCK = new ReentrantLock();

    private static volatile ReentrantLockThreadSafeSingleton singleton;

    public static ReentrantLockThreadSafeSingleton getInstance() {
        if (singleton == null) {
            REENTRANT_LOCK.lock();

            try {
                singleton = new ReentrantLockThreadSafeSingleton();
            } finally {
                REENTRANT_LOCK.unlock();
            }
        }

        return singleton;
    }
}
