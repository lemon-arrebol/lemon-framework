package com.lemon.designpattern.singleton.hungry;

/**
 * @author lemon
 * @version 1.0
 * @description: 线程安全静态内部类单例模式
 * @date Create by lemon on 2019-11-08 13:48
 */
public class StaticInnerClassSingleton {
    private StaticInnerClassSingleton() {

    }

    private static class SingletonHolder {
        static final StaticInnerClassSingleton SINGLETON = new StaticInnerClassSingleton();
    }

    public static StaticInnerClassSingleton getInstance() {
        return SingletonHolder.SINGLETON;
    }
}
