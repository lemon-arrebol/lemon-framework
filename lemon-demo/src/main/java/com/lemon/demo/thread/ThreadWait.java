package com.lemon.demo.thread;

/**
 * @author lemon
 * @return
 * @description wait()可以让线程从运行态转换为阻塞态，同时还会释放线程的同步锁。
 * @date 2019-11-11 15:20
 */
public class ThreadWait {
    // 定义锁
    private static final Object LOCK = new Object();

    public static void main(String[] args) throws InterruptedException {

        Thread thread1 = new Thread(() -> {
            synchronized (LOCK) {
                try {
                    System.out.println("线程1抢到了锁....");
                    LOCK.wait();
                    System.out.println("线程1运行结束.....");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (LOCK) {
                    System.out.println("线程2抢到了锁....");
                    System.out.println("线程2运行结束.....");
                }
            }
        });

        thread1.start();//开启线程1
        //让主线程稍等片刻,确保线程1已经运行
        Thread.sleep(200);
        thread2.start();//开启线程2
    }
}