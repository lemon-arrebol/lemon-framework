package com.lemon.designpattern.singleton.lazy;

/**
 * @author lemon
 * @version 1.0
 * @description: 方法内部synchronized线程安全的单例模式
 * @date Create by lemon on 2019-11-08 16:57
 */
public class MonitorSynchronizedThreadSafeSingleton {
    private MonitorSynchronizedThreadSafeSingleton() {

    }

    private static final Object MONITOR = new Object();

    private static volatile MonitorSynchronizedThreadSafeSingleton singleton;

    public static MonitorSynchronizedThreadSafeSingleton getInstance() {
        if (singleton == null) {
            synchronized (MonitorSynchronizedThreadSafeSingleton.MONITOR) {
                if (singleton == null) {
                    singleton = new MonitorSynchronizedThreadSafeSingleton();
                }
            }
        }

        return singleton;
    }
}
