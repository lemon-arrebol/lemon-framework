package com.lemon.study.spi.serviceloader.service.impl;

import com.google.auto.service.AutoService;
import com.lemon.study.spi.serviceloader.service.LemonServiceLoader;

/**
 * @author lemon
 * @version 1.0
 * @description: TODO
 * @date Create by lemon on 2019-08-02 11:39
 */
@AutoService(LemonServiceLoader.class)
public final class LemonServiceLoaderImpl implements LemonServiceLoader {
    /**
     * @param clazz@return
     * @description 返回给定注入类型的相应实例
     * @author lemon
     * @date 2019-08-02 11:40
     */
    @Override
    public <T> T getInstance(Class<T> clazz) {
        return null;
    }

    /**
     * @param clazz
     * @param name
     * @return
     * @description 返回给定注入类型和名称的相应实例
     * @author lemon
     * @date 2019-08-02 11:40
     */
    @Override
    public <T> T getInstance(Class<T> clazz, String name) {
        return null;
    }
}
