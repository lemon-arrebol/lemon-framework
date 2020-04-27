package com.lemon.demo.thread;

/**
 * @author lemon
 * @return
 * @description 而yield()会让线程交出CPU的执行权，但是不会释放锁。
 * (1) sleep()会使线程进入阻塞状态，yield()不会时线程进入阻塞态而是进入可运行态，当线程重新获得CPU执行权后又可以执行。
 * (2) sleep()释放CPU后其他都可以竞争CPU的执行权，而yield()只会让线程优先级大于等于自己的线程竞争CPU执行权的机会。
 * @date 2019-11-11 15:02
 */
public class ThreadYield implements Runnable {
    // 定义锁
    private static final Object LOCK = new Object();

    private int count = 0;// 系统访问次数

    /**
     * @param
     * @return void
     * @description 执行多遍，结果可发现，都是首先输出：1线程: 第1位访客来啦！
     * 所以可以看出 yield 不会释放对象锁，别的线程进入不了。
     * @author lemon
     * @date 2019-11-11 17:50
     */
    @Override
    public void run() {// 通过synchronized实现同步
        synchronized (LOCK) {
            count++;
            System.out.println(Thread.currentThread().getName() + "线程: " + "第" + count + "位访客来啦！");

            try {
                if (count == 1) {
                    /// <注>. sleep 和 yield 都不会释放 <锁资源>。
                    // Thread.sleep(5000);
                    Thread.yield();

                    // this.wait();
                }
            } catch (Exception e) {
                // TODO: handle exception
            }

            System.out.println(Thread.currentThread().getName() + "线程: " + "第" + count + "位访客离开！");
        }
    }

    public static void main(String[] args) {
        ThreadYield rd = new ThreadYield();

        for (int i = 1; i <= 50; i++) {
            Thread thread = new Thread(rd, String.valueOf(i));
            // 一定要注意不是通过.run()启动线程
            thread.start();
        }
    }
}