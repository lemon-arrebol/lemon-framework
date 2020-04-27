package com.lemon.thread.designpattern.guardedsuspension;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Timer;
import java.util.TimerTask;

@Slf4j
public class ObjectBlockerTest {
    private volatile boolean isStateOK = true;

    private final Blocker blocker = new ObjectBlocker();

    private final Predicate stateBeOK = () -> isStateOK;

    @Test
    public void callWithGuardedAction() throws InterruptedException {
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                String result = ObjectBlockerTest.this.guarededMethod("test " + i);
                ObjectBlockerTest.log.info("guarededMethod ====== " + result);

                try {
                    Thread.sleep(Math.round(100));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
        Timer timer = new Timer();

        // 延迟50ms调用helper.stateChanged方法
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                ObjectBlockerTest.this.stateChanged();
            }
        }, 50, 10);

        Thread.sleep(100000);
        timer.cancel();
    }

    public String guarededMethod(final String message) {
        GuardedAction<String> guardedAction = new GuardedAction<String>(stateBeOK) {
            @Override
            public String call() {
                return message + "->received.";
            }
        };

        String result = null;

        try {
            result = blocker.callWithGuardedAction(guardedAction);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public void stateChanged() {
        try {
            blocker.signalAfter(() -> {
                isStateOK = true;
                ObjectBlockerTest.log.info("state ok.");
                return Boolean.TRUE;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}