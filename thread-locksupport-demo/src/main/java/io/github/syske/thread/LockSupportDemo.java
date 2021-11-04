package io.github.syske.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @program: thread-locksupport-demo
 * @description:
 * @author: syske
 * @date: 2021-11-04 13:16
 */
public class LockSupportDemo {
    public static void main(String[] args) {
        String blocker = "321123";
        Thread t = new Thread(() -> {
            LockSupport.parkUntil(TimeUnit.SECONDS.toNanos(1));
            System.out.println("开始解锁");
            LockSupport.park(blocker);
        });
        t.start();

        LockSupport.unpark(t);
        System.out.println("end");
    }
}
