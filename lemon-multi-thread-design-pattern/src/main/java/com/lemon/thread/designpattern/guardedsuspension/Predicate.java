package com.lemon.thread.designpattern.guardedsuspension;

/**
 * @author lemon
 * @version 1.0
 * @description: Action的保护条件
 * @date Create by lemon on 2019-11-07 17:52
 */
@FunctionalInterface
public interface Predicate {
    /**
     * @param
     * @return true Action执行；false Action不执行
     * @description
     * @author lemon
     * @date 2019-11-07 17:52
     */
    boolean evaluate();
}
