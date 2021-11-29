/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.thread;

/**
 * @author syske
 * @version 1.0
 * @date 2021-11-09 22:03
 */
public class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getState());
        System.out.println("run 方法开始执行了");
    }

    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        State state = myThread.getState();
        System.out.println(state);
        myThread.start();
        System.out.println(myThread.getState());
    }
}
