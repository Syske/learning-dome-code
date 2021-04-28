package io.github.syske.java.learning.thread;

/**
 * @program: java-learning
 * @description:
 * @author: syske
 * @create: 2021-03-20 22:18
 */
public class Mythread2 extends Thread {
    private int count = 5;

    @Override
    public synchronized void run() {
        super.run();
        count--;
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + ":" + count);
    }

    public static void main(String[] args) {
        Mythread2 mythread2 = new Mythread2();
        Thread mythread2_1 = new Thread(mythread2, "t1");
        Thread mythread2_2 = new Thread(mythread2, "t2");
        Thread mythread2_3 = new Thread(mythread2, "t3");
        Thread mythread2_4 = new Thread(mythread2, "t4");
        Thread mythread2_5 = new Thread(mythread2, "t5");
        Thread mythread2_6 = new Thread(mythread2, "t6");
        Thread mythread2_7 = new Thread(mythread2, "t7");
        Thread mythread2_8 = new Thread(mythread2, "t8");
        Thread mythread2_9 = new Thread(mythread2, "t9");
        mythread2_1.start();
        mythread2_2.start();
        mythread2_3.start();
        mythread2_4.start();
        mythread2_5.start();
        mythread2_6.start();
        mythread2_7.start();
        mythread2_8.start();
        mythread2_9.start();
    }
}
