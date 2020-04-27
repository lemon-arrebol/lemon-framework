package com.lemon.thread.designpattern.guardedsuspension;

import com.google.common.base.Preconditions;

import java.util.concurrent.Callable;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lemon
 * @version 1.0
 * @description: 受保护的Action需要满足指定条件才能执行，否则挂起等待条件满足后被唤醒
 * @date Create by lemon on 2019-11-07 18:00
 */
public class ConditionBlocker implements Blocker {
    private final Lock lock;

    private final Condition condition;

    public ConditionBlocker() {
        this(new ReentrantLock());
    }

    public ConditionBlocker(Lock lock) {
        this.lock = lock;
        this.condition = lock.newCondition();
    }

    /**
     * @param guardedAction 带保护条件的目标动作
     * @return void
     * @description 在保护条件成立时执行目标动作，否则阻塞当前线程，直到保护条件成立。
     * @author lemon
     * @date 2019-11-07 17:58
     */
    @Override
    public <V> V callWithGuardedAction(GuardedAction<V> guardedAction) throws Exception {
        Preconditions.checkArgument(guardedAction != null, "GuardedAction not allowed to be null");

        Predicate guardedPredicate = guardedAction.guardedPredicate;

        Preconditions.checkArgument(guardedPredicate != null, "Predicate of GuardedAction not allowed to be null");

        this.lock.lockInterruptibly();

        try {
            // 可能会发生中断和虚假唤醒
            while (!guardedPredicate.evaluate()) {
                this.condition.await();
            }

            return guardedAction.call();
        } finally {
            lock.unlock();
        }
    }

    /**
     * @return void
     * @description 唤醒本Blocker所暂挂的所有线程中的一个线程。
     * @author lemon
     * @date 2019-11-07 17:59
     */
    @Override
    public void signal() throws InterruptedException {
        this.lock.lockInterruptibly();

        try {
            this.condition.signal();
        } finally {
            lock.unlock();
        }
    }

    /**
     * @return void
     * @description 唤醒本Blocker所暂挂的所有线程。
     * @author lemon
     * @date 2019-11-07 17:59
     */
    @Override
    public void signalAll() throws InterruptedException {
        this.lock.lockInterruptibly();

        try {
            this.condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    /**
     * @param operation
     * @return void
     * @description 执行 operation 所指定的操作后，决定是否唤醒本Blocker所暂挂的所有线程中的一个线程。
     * @author lemon
     * @date 2019-11-07 17:58
     */
    @Override
    public void signalAfter(Callable<Boolean> operation) throws Exception {
        Preconditions.checkArgument(operation != null, "Operation not allowed to be null");

        this.lock.lockInterruptibly();

        try {
            if (operation.call()) {
                this.condition.signal();
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * @param operation
     * @return void
     * @description 执行 operation 所指定的操作后，决定是否唤醒本Blocker所暂挂的所有线程。
     * @author lemon
     * @date 2019-11-07 17:59
     */
    @Override
    public void broadcastAfter(Callable<Boolean> operation) throws Exception {
        Preconditions.checkArgument(operation != null, "Operation not allowed to be null");

        this.lock.lockInterruptibly();

        try {
            if (operation.call()) {
                this.condition.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }
}
