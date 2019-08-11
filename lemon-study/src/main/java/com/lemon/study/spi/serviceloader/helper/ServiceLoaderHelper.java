package com.lemon.study.spi.serviceloader.helper;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import java.util.*;

/**
 * @author lemon
 * @version 1.0
 * @description: TODO
 * @date Create by lemon on 2019-08-02 12:57
 */
public class ServiceLoaderHelper {
    /**
     * @param
     * @return
     * @description 获取所有clazz的实现类
     * @author lemon
     * @date 2019-08-02 13:43
     */
    public static <T> Iterator<T> iterator(Class<T> clazz) {
        Preconditions.checkNotNull(clazz, "Service interface cannot be null");
        ServiceLoader<T> loader = ServiceLoader.load(clazz);

        return loader.iterator();
    }

    /**
     * @param
     * @return
     * @description 获取所有clazz的实现类
     * @author lemon
     * @date 2019-08-02 13:44
     */
    public static <T> List<T> loadAll(Class<T> clazz) {
        Preconditions.checkNotNull(clazz, "Service interface cannot be null");

        return ServiceLoaderHelper.loadAllOrdered(clazz, null);
    }

    /**
     * @param
     * @return
     * @description 获取所有clazz的实现类，并按照comparator排序
     * @author lemon
     * @date 2019-08-02 13:44
     */
    public static <T> List<T> loadAllOrdered(Class<T> clazz, Comparator<T> comparator) {
        Preconditions.checkNotNull(clazz, "Service interface cannot be null");
        Iterator<T> iterator = ServiceLoaderHelper.iterator(clazz);
        List<T> candidates = Lists.newArrayList(iterator);

        if (comparator != null) {
            Collections.sort(candidates, comparator);
        }

        return candidates;
    }

    /**
     * @param
     * @return
     * @description 获取clazz第一个实现类
     * @author lemon
     * @date 2019-08-02 13:43
     */
    public static <T> Optional<T> loadFirst(Class<T> clazz) {
        Preconditions.checkNotNull(clazz, "Service interface cannot be null");
        Iterator<T> iterator = ServiceLoaderHelper.iterator(clazz);
        ServiceLoaderHelper.checkIterator(iterator, clazz);

        return Optional.ofNullable(iterator.next());
    }

    /**
     * @param
     * @return
     * @description 获取clazz最后一个实现类
     * @author lemon
     * @date 2019-08-02 13:44
     */
    public static <T> Optional<T> loadLast(Class<T> clazz) {
        Preconditions.checkNotNull(clazz, "Service interface cannot be null");
        List<T> candidates = ServiceLoaderHelper.loadAll(clazz);

        if (candidates.isEmpty()) {
            return Optional.empty();
        }

        return Optional.ofNullable(candidates.get(candidates.size() - 1));
    }

    /**
     * @param
     * @return
     * @description 检查是否有clazz的实现类
     * @author lemon
     * @date 2019-08-02 13:48
     */
    private static <T> void checkIterator(Iterator<T> iterator, Class<T> clazz) {
        if (!iterator.hasNext()) {
            throw new IllegalStateException(String.format(
                    "No implementation defined in /META-INF/services/%s, please check whether the file exists and has the right implementation class!",
                    clazz.getName()));
        }
    }
}
