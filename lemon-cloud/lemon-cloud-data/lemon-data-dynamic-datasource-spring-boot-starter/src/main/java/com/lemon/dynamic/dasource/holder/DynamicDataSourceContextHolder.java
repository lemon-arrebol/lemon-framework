package com.lemon.dynamic.dasource.holder;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @param
 * @author lemon
 * @description 动态数据源上下文标识
 * @return
 * @date 2019-08-10 10:17
 */
@Slf4j
public class DynamicDataSourceContextHolder {
    private static final ThreadLocal<Deque<Object>> DATA_SOURCE_CONTEXT_HOLDER = new ThreadLocal() {
        @Override
        protected Object initialValue() {
            return new ArrayDeque();
        }
    };

    /**
     * @description 数据源的 key集合，用于切换时判断数据源是否存在
     * @param
     * @return
     * @author lemon
     * @date 2019-08-10 10:33
     */
    private static final List<Object> ALL_DATA_SOURCE_KEY_LIST = Lists.newArrayList();

    /**
     * @param
     * @return
     * @description 保存当前数据源标识，将给定元素”压入”栈中。存入的元素会在栈首。即:栈的第一个元素
     * @author lemon
     * @date 2019-08-10 10:36
     */
    public static void pushDataSourceKey(Object key) {
        DynamicDataSourceContextHolder.DATA_SOURCE_CONTEXT_HOLDER.get().push(key);
    }

    /**
     * @param
     * @return
     * @description 获取当前数据源标识，返回队首元素，但是不删除
     * @author lemon
     * @date 2019-08-10 10:36
     */
    public static Object peekDataSourceKey() {
        return DynamicDataSourceContextHolder.DATA_SOURCE_CONTEXT_HOLDER.get().peek();
    }

    /**
     * @param
     * @return
     * @description 清除当前数据源标识，从队首删除并返回该元素
     * @author lemon
     * @date 2019-08-10 10:36
     */
    public static void pollDataSourceKey() {
        Deque<Object> deque = DynamicDataSourceContextHolder.DATA_SOURCE_CONTEXT_HOLDER.get();
        deque.poll();

        if (deque.isEmpty()) {
            DynamicDataSourceContextHolder.DATA_SOURCE_CONTEXT_HOLDER.remove();
        }
    }

    /**
     * @param
     * @return
     * @description 清除当前线程所有数据源标识
     * @author lemon
     * @date 2019-08-10 10:36
     */
    public static void printDataSourceKey() {
        if (log.isDebugEnabled()) {
            Iterator<Object> iterator = DynamicDataSourceContextHolder.DATA_SOURCE_CONTEXT_HOLDER.get().iterator();
            StringBuilder result = new StringBuilder();
            result.append("start-->");

            while (iterator.hasNext()) {
                result.append(iterator.next()).append("-->");
            }

            result.append("end");
            log.debug(result.toString());
        }
    }

    /**
     * @param
     * @return
     * @description 清除当前线程所有数据源标识
     * @author lemon
     * @date 2019-08-10 10:36
     */
    public static void clearDataSourceKey() {
        DynamicDataSourceContextHolder.DATA_SOURCE_CONTEXT_HOLDER.remove();
    }

    /**
     * @param
     * @return
     * @description 判断是否包含指定数据源标识
     * @author lemon
     * @date 2019-08-10 10:37
     */
    public static boolean containDataSourceKey(Object key) {
        return DynamicDataSourceContextHolder.ALL_DATA_SOURCE_KEY_LIST.contains(key);
    }

    /**
     * @param
     * @return
     * @description 添加数据源标识
     * @author lemon
     * @date 2019-08-10 10:37
     */
    public static boolean addDataSourceKey(Object key) {
        return DynamicDataSourceContextHolder.ALL_DATA_SOURCE_KEY_LIST.add(key);
    }

    /**
     * @param
     * @return
     * @description 添加数据源标识
     * @author lemon
     * @date 2019-08-10 10:37
     */
    public static boolean addDataSourceKeys(Collection<? extends Object> keys) {
        return DynamicDataSourceContextHolder.ALL_DATA_SOURCE_KEY_LIST.addAll(keys);
    }

    /**
     * @param
     * @return
     * @description 清空所有数据源标识
     * @author lemon
     * @date 2019-08-10 10:37
     */
    public static void clearDataSourceKeys() {
        DynamicDataSourceContextHolder.ALL_DATA_SOURCE_KEY_LIST.clear();
    }
}