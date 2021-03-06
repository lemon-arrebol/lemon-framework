package com.lemon.thread.designpattern.guardedsuspension;

import com.google.common.base.Preconditions;

import java.util.concurrent.Callable;

/**
 * @author lemon
 * @version 1.0
 * @description: 受保护的Action需要满足指定条件才能执行，否则挂起等待条件满足后被唤醒
 * @date Create by lemon on 2019-11-07 18:00
 */
public class ObjectBlocker implements Blocker {
    private final Object monitor;

    public ObjectBlocker() {
        this(new Object());
    }

    public ObjectBlocker(Object monitor) {
        this.monitor = monitor;
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

        synchronized (this.monitor) {
            // 可能会发生中断和虚假唤醒
            while (!guardedPredicate.evaluate()) {
                this.monitor.wait();
            }

            return guardedAction.call();
        }
    }

    /**
     * @return void
     * @description 唤醒本Blocker所暂挂的所有线程中的一个线程。
     * @author lemon
     * @date 2019-11-07 17:59
     */
    @Override
    public void signal() {
        synchronized (this.monitor) {
            this.monitor.notify();
        }
    }

    /**
     * @return void
     * @description 唤醒本Blocker所暂挂的所有线程。
     * @author lemon
     * @date 2019-11-07 17:59
     */
    @Override
    public void signalAll() {
        synchronized (this.monitor) {
            this.monitor.notifyAll();
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

        synchronized (this.monitor) {
            if (operation.call()) {
                this.monitor.notify();
            }
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

        synchronized (this.monitor) {
            if (operation.call()) {
                this.monitor.notifyAll();
            }
        }
    }
}
