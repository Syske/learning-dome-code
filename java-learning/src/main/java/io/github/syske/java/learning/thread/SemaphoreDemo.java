package io.github.syske.java.learning.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author syske
 */
public class SemaphoreDemo {
    private static final int THREAD_SIZE = 30;
    private static final Semaphore s = new Semaphore(5);

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_SIZE);
        for (int i = 0; i < THREAD_SIZE*2; i++) {
            int finalI = i;
            executorService.execute(() -> {
                try {
                    s.acquire();
                    System.out.println(Thread.currentThread().getName() + " currentTimeMillis: "+ System.currentTimeMillis());
                    Thread.sleep(2000);
                    System.out.println("thread count: " + finalI);
                    s.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();
    }
}
