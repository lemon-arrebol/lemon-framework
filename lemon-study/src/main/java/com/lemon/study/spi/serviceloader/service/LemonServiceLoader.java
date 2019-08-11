package com.lemon.study.spi.serviceloader.service;

/**
 * @author lemon
 * @version 1.0
 * @description: 获取实例
 * @date Create by lemon on 2019-08-02 11:38
 */
public interface LemonServiceLoader {
    /**
     * @param
     * @return
     * @description 返回给定注入类型的相应实例
     * @author lemon
     * @date 2019-08-02 11:40
     */
    <T> T getInstance(Class<T> clazz);

    /**
     * @param
     * @return
     * @description 返回给定注入类型和名称的相应实例
     * @author lemon
     * @date 2019-08-02 11:40
     */
    <T> T getInstance(Class<T> clazz, String name);
}
