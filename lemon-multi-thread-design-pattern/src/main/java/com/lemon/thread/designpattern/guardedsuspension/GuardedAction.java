package com.lemon.thread.designpattern.guardedsuspension;

import java.util.concurrent.Callable;

/**
 * @author lemon
 * @version 1.0
 * @description: 受保护的Action
 * @date Create by lemon on 2019-11-07 17:53
 */
public abstract class GuardedAction<V> implements Callable<V> {
    protected Predicate guardedPredicate;

    /**
     * @param guardedPredicate
     * @return
     * @description 受保护的动作
     * @author lemon
     * @date 2019-11-07 18:09
     */
    public GuardedAction(Predicate guardedPredicate) {
        this.guardedPredicate = guardedPredicate;
    }
}
