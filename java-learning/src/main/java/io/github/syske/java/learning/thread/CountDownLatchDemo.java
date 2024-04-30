package io.github.syske.java.learning.thread;

import java.util.concurrent.*;

public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        Runnable a = () -> {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("a");
            countDownLatch.countDown();
        };
        Runnable b = () -> {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("b");
            countDownLatch.countDown();
        };
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 2, 1, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>());
        threadPoolExecutor.execute(a);
        threadPoolExecutor.execute(b);

        countDownLatch.await();
        System.out.println("end");
        threadPoolExecutor.shutdown();
    }
}
