package com.lemon.demo.thread;

/**
 * @author lemon
 * @return
 * @description join方法释放的是被调用join()方法的线程锁，而不是调用方。 join()可以保证让一个线程在另一个线程之前执行结束。
 * 如何保证一个工作在另一个工作结束之前完成，就可以使用join()方法。
 * @date 2019-11-11 17:07
 */
public class JoinThread {
    // 定义锁
    private static final Object LOCK = new Object();

    public static void main(String[] args) throws InterruptedException {
        // otherObjectLock();

        // joinThreadLock();

        joinThreadLockWithWait();
    }

    /**
     * @return void
     * @description join方法释放的是被调用join()方法的线程锁，而不是调用方
     * @author lemon
     * @date 2019-11-14 17:36
     */
    private static void otherObjectLock() throws InterruptedException {
        final Thread thread1 = new Thread(() -> {
            synchronized (LOCK) {
                try {
                    System.out.println("线程1开始运行.....");
                    // 为了验证线程1在线程2之前运行结束，这里让线程1睡眠3秒
                    Thread.sleep(3000);
                    System.out.println("线程1运行结束.....");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            // 调用join不会释放锁
            synchronized (LOCK) {
                try {
                    System.out.println("线程2开始运行 获取了锁.....");
                    // 确保thread1线程已启动
                    Thread.sleep(200);
                    // 线程2运行到这里会等待线程1运行结束
                    thread1.join();
                    System.out.println("线程2运行结束.....");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread2.setName("thread2-join");
        thread2.start();// 先开启线程2
        Thread.sleep(10);
        thread1.setName("thread1-join");
        thread1.start();// 在开启线程1
    }

    /**
     * @return void
     * @description thread1.join()释放的是thread1持有的锁
     * @author lemon
     * @date 2019-11-14 18:02
     */
    private static void joinThreadLock() throws InterruptedException {
        class MyThread extends Thread {
            private String name;

            public MyThread(String name) {
                this.name = name;
            }

            @Override
            public void run() {
                synchronized (this) {
                    for (int i = 0; i < 100; i++) {
                        System.out.println(name + i);
                    }
                }
            }

        }

        Thread thread1 = new MyThread("thread1 -- ");
        thread1.start();

        synchronized (thread1) {
            for (int i = 0; i < 100; i++) {
                if (i == 20) {
                    try {
                        thread1.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + " -- " + i);
            }
        }
    }

    /**
     * @return void
     * @description wait模拟join方法，效果一样
     * @author lemon
     * @date 2019-11-14 18:04
     */
    private static void joinThreadLockWithWait() throws InterruptedException {
        class MyThread extends Thread {
            private String name;
            private Object oo;

            public MyThread(String name, Object oo) {
                this.name = name;
                this.oo = oo;
            }

            @Override
            public void run() {
                synchronized (oo) {
                    for (int i = 0; i < 100; i++) {
                        System.out.println(name + i);
                    }

                    oo.notifyAll();
                }
            }
        }

        Thread thread1 = new MyThread("thread1 -- ", LOCK);
        thread1.start();

        synchronized (LOCK) {
            for (int i = 0; i < 100; i++) {
                if (i == 20) {
                    try {
                        LOCK.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + " -- " + i);
            }
        }
    }
}