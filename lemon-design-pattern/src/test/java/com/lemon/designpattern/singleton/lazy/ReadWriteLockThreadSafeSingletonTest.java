package com.lemon.designpattern.singleton.lazy;

import org.junit.Test;

import java.util.Calendar;

public class ReadWriteLockThreadSafeSingletonTest {
    @Test
    public void test() throws InterruptedException {
        for (int i = 0; i < 50; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    ReadWriteLockThreadSafeSingleton singleton = ReadWriteLockThreadSafeSingleton.getInstance();
                    System.out.println(Thread.currentThread().getName() + "-" + j + "  " + (Calendar.getInstance().getTimeInMillis()) + "  " + singleton);
                }
            }).start();
        }

        Thread.sleep(10000);
    }
}