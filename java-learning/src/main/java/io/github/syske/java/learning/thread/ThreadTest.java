package io.github.syske.java.learning.thread;

import java.util.Date;
import java.util.concurrent.Callable;

public class ThreadTest {
    public static void main(String[] args) {
        long i = 0;
        long j = new Date().getTime();
        Thread thread1 = new Thread(new MyRunnable("thread1"));
        Thread thread2 = new Thread(new MyRunnable("thread2"));
        thread1.start();
        thread2.start();
        while (i < j + 10000) {

            try {
                thread1.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i = new Date().getTime();
        }

    }
}

class MyRunnable implements Runnable {
    private String name;

    public MyRunnable() {
        super();
    }

    public MyRunnable(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println(this.name + " : " + new Date().getTime());
    }
}
