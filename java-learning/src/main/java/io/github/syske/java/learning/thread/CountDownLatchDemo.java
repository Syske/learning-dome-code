package io.github.syske.java.learning.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class CountDownLatchDemo {

    public void main(String[] args) throws InterruptedException {
        //testABC();
        AtomicInteger count = new AtomicInteger(0);
        Runnable a = () -> {
            System.out.print("A");
        };
        Runnable b = () -> {
            System.out.print("B");
        };

        new Thread(a).start();
        new Thread(b).start();


    }

    private static void testABC() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            printABC();
        }

        AtomicInteger count = new AtomicInteger(0);
        count.incrementAndGet();
        if (count.get() == 0) {

        }
    }

    private static void printABC() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        Runnable a = () -> {
            System.out.print("A");
            countDownLatch.countDown();
            long count = countDownLatch.getCount();
            System.out.println(count);
        };
        Runnable b = () -> {
            System.out.print("B");
            countDownLatch.countDown();
        };

        Runnable c = () -> {
            System.out.print("C");
            countDownLatch.countDown();
        };
        Thread thread = new Thread(a);
        c.notifyAll();
        thread.start();
        new Thread(b).start();
        new Thread(c).start();
        countDownLatch.await();
    }

    class Foo {
        public void first() { System.out.print("first"); }
        public void second() { System.out.print("second"); }
        public void third() {
            System.out.print("third"); }
    }

    class Foo2 {
        AtomicInteger count = new AtomicInteger(0);
        public Foo2(Runnable runnable) {

        }

        public void first(Runnable printFirst) throws InterruptedException {

            // printFirst.run() outputs "first". Do not change or remove this line.
            if (count.get() == 0) {
                printFirst.run();
                count.incrementAndGet();
            } else {
                printFirst.wait();
                printFirst.notifyAll();
            }
        }

        public void second(Runnable printSecond) throws InterruptedException {

            // printSecond.run() outputs "second". Do not change or remove this line.
            if (count.get() == 1) {
                printSecond.run();
                count.incrementAndGet();
            } else {
                printSecond.wait();
                printSecond.notifyAll();
            }
        }

        public void third(Runnable printThird) throws InterruptedException {

            // printThird.run() outputs "third". Do not change or remove this line.
            if(count.get() == 2) {
                printThird.run();
                count.incrementAndGet();
                count.set(0);
            } else {
                printThird.wait();
                printThird.notifyAll();
            }
        }
    }
}
