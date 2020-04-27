package com.lemon.demo.thread;

/**
 * @author lemon
 * @return
 * @description 在java中虚拟机运行时，强制将运行的线程停止（this.stop()）或对于锁定的对象强制"解锁"两种场景，造成数据不一致，
 * 会抛出 java.lang.ThreadDeath 线程死亡 java.lang.Error 错误
 * @date 2019-11-19 10:56
 */
public class ThreadDeathTest {
    private static Thread theadDemo;

    public static void main(String[] args) {
        theadDemo = new Thread(() -> {
            try {
                try {
                    theadDemo.stop();
                } catch (ThreadDeath e) {
                    System.out.println("ThreadDeath 模拟异常已经发生!!!");
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        theadDemo.start();
    }
}