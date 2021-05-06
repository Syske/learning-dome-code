package io.github.syske.java.learning.thread;

/**
 * @program: java-learning
 * @description:
 * @author: syske
 * @create: 2021-03-20 21:57
 */
public class MyThread extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            System.out.println("runName = " + Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        myThread.setName("myThread");
        myThread.start();

        for (int i = 0; i < 1000; i++) {
            System.out.println("main = " + Thread.currentThread().getName());
        }
    }
}
