package com.lemon.designpattern.singleton.lazy;

import java.util.Calendar;
import java.util.concurrent.locks.StampedLock;

/**
 * @author lemon
 * @version 1.0
 * @description: 可重入读写锁实现高效的线程安全的单例模式
 * @date Create by lemon on 2019-11-08 16:57
 */
public class StampedLockThreadSafeSingleton {
    private StampedLockThreadSafeSingleton() {

    }

    private static final StampedLock STAMPED_LOCK = new StampedLock();

    private static volatile StampedLockThreadSafeSingleton singleton;

    public static StampedLockThreadSafeSingleton getInstance() {
        if (singleton == null) {
            // tryOptimisticRead是一个乐观的读，使用这种锁的读不阻塞写，每次读的时候得到一个当前的stamp值（类似时间戳的作用）
            long stamp = STAMPED_LOCK.tryOptimisticRead();

            /**如果读取的时候发生了写，则stampedLock的stamp属性值会变化，此时需要重读，
             * 再重读的时候需要加读锁（并且重读时使用的应当是悲观的读锁，即阻塞写的读锁），
             * 当然重读的时候还可以使用tryOptimisticRead，此时需要结合循环了，即类似CAS方式，读锁又重新返回一个stampe值*/
            if (!STAMPED_LOCK.validate(stamp)) {
                stamp = STAMPED_LOCK.readLock();
            }

//            while (!STAMPED_LOCK.validate(stamp)) {
//                stamp = STAMPED_LOCK.tryOptimisticRead();
//            }

            try {
                stamp = STAMPED_LOCK.writeLock();
                System.out.println(Thread.currentThread().getName() + " writeLock " + Calendar.getInstance().getTimeInMillis());

                if (singleton == null) {
                    System.out.println(Thread.currentThread().getName() + "-" + Calendar.getInstance().getTimeInMillis());

                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    singleton = new StampedLockThreadSafeSingleton();
                }
            } finally {
                STAMPED_LOCK.unlockWrite(stamp);//释放读锁
            }
        }

        return singleton;
    }
}
