package com.lemon.designpattern.singleton.hungry;

/**
 * @author lemon
 * @version 1.0
 * @description: 线程安全静态常量单例模式
 * @date Create by lemon on 2019-11-08 13:45
 */
public class StaticConstantSingleton {
    private StaticConstantSingleton() {

    }

    /**
     * 在类被加载时初始化并仅被初始化一次，这样就可以保证只有一个instance被初始化
     */
    private static final StaticConstantSingleton SINGLETON = new StaticConstantSingleton();

    public static StaticConstantSingleton getInstance() {
        return StaticConstantSingleton.SINGLETON;
    }
}
