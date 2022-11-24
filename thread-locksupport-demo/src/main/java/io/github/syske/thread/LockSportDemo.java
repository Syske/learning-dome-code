/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.LockSupport;

/**
 * @author syske
 * @version 1.0
 * @date 2021-11-04 8:48
 */
public class LockSportDemo {
    static ExecutorService executorService = Executors.newSingleThreadExecutor();
    public static void main(String[] args) {
        LockSupport.parkNanos(1000L);
        System.out.println("hello lock support");
        LockSupport.unpark(Thread.currentThread());


        Task command = new Task();
//        Thread thread = new Thread(command);
//        thread.start();
//        executorService.execute(command);
        String testBlocker = "123123";
        LockSupport.parkNanos(testBlocker, 1000000L);
        System.out.println("helllo");
        System.out.println(testBlocker);
        System.out.println("helllo2");
    }

    static class Task implements Runnable {
        @Override
        public void run() {
            System.out.println("我要开始阻塞了");
            LockSupport.park();
            System.out.println("我被激活了");
            System.out.println("我要关闭线程池了");
//            executorService.shutdown();
        }
    }
}
