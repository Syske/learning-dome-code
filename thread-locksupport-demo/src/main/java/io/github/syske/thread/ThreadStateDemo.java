package io.github.syske.thread;

import java.util.concurrent.TimeUnit;

/**
 * @program: thread-locksupport-demo
 * @description:
 * @author: syske
 * @date: 2021-11-10 13:26
 */
public class ThreadStateDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread();
        System.out.println(thread.getState());
        thread.start();
        System.out.println(thread.getState());
        TimeUnit.SECONDS.sleep(1L);
        System.out.println(thread.getState());
    }
}
