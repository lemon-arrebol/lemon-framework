package com.lemon.thread.designpattern.guardedsuspension;

import java.util.concurrent.Callable;

/**
 * @author lemon
 * @version 1.0
 * @description: TODO
 * @date Create by lemon on 2019-11-07 17:55
 */
public interface Blocker {
    /**
     * @param guardedAction 带保护条件的目标动作
     * @return void
     * @description 在保护条件成立时执行目标动作，否则阻塞当前线程，直到保护条件成立。
     * @author lemon
     * @date 2019-11-07 17:58
     */
    <V> V callWithGuardedAction(GuardedAction<V> guardedAction) throws Exception;

    /**
     * @param
     * @return void
     * @description 唤醒本Blocker所暂挂的所有线程中的一个线程。
     * @author lemon
     * @date 2019-11-07 17:59
     */
    void signal() throws InterruptedException;

    /**
     * @param
     * @return void
     * @description 唤醒本Blocker所暂挂的所有线程。
     * @author lemon
     * @date 2019-11-07 17:59
     */
    void signalAll() throws InterruptedException;

    /**
     * @param operation
     * @return void
     * @description 执行 operation 所指定的操作后，决定是否唤醒本Blocker所暂挂的所有线程中的一个线程。
     * @author lemon
     * @date 2019-11-07 17:58
     */
    void signalAfter(Callable<Boolean> operation) throws Exception;

    /**
     * @param operation
     * @return void
     * @description 执行 operation 所指定的操作后，决定是否唤醒本Blocker所暂挂的所有线程。
     * @author lemon
     * @date 2019-11-07 17:59
     */
    void broadcastAfter(Callable<Boolean> operation) throws Exception;
}
