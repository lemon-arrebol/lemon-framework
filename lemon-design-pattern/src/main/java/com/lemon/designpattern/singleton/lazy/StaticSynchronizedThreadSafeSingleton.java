package com.lemon.designpattern.singleton.lazy;

/**
 * @author lemon
 * @version 1.0
 * @description: 静态方法synchronized线程安全的单例模式
 * @date Create by lemon on 2019-11-08 16:57
 */
public class StaticSynchronizedThreadSafeSingleton {
    private StaticSynchronizedThreadSafeSingleton() {

    }

    private static StaticSynchronizedThreadSafeSingleton singleton;

    public static synchronized StaticSynchronizedThreadSafeSingleton getInstance() {
        if (singleton == null) {
            singleton = new StaticSynchronizedThreadSafeSingleton();
        }

        return singleton;
    }
}
