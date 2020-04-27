package com.lemon.demo.thread;

/**
 * @author lemon
 * @return
 * @description sleep()会让线程交出CPU的执行权，但是不会释放锁。
 * (1) sleep()会使线程进入阻塞状态，yield()不会时线程进入阻塞态而是进入可运行态，当线程重新获得CPU执行权后又可以执行。
 * (2) sleep()释放CPU后其他都可以竞争CPU的执行权，而yield()只会让线程优先级大于等于自己的线程竞争CPU执行权的机会。
 * @date 2019-11-11 15:02
 */
public class ThreadSleep {
    // 定义锁
    private static final Object LOCK = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程1开启运行....");
                synchronized (LOCK) {
                    try {
                        System.out.println("线程1抢到了锁....");
                        Thread.sleep(2000);
                        System.out.println("线程1运行结束.....");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程2开启运行....");
                synchronized (LOCK) {
                    System.out.println("线程2抢到了锁....");
                    System.out.println("线程2运行结束.....");
                }
            }
        });

        thread1.start();//开启线程1
        //让主线程稍等片刻,确保线程1已经运行
        Thread.sleep(300);
        thread2.start();//开启线程2
    }
}