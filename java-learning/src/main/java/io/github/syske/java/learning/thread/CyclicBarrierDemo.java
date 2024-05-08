package io.github.syske.java.learning.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class CyclicBarrierDemo {
    static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 20; i++) {
            executorService.submit(() -> {
                try {
                    test2();
                } catch (BrokenBarrierException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        executorService.shutdown();
    }

    private static void test2() throws BrokenBarrierException, InterruptedException {
        final CyclicBarrier cyclicBarrierA = new CyclicBarrier(1, () -> {
            System.out.print("A");
            System.out.print("B");
            System.out.print("C");
        });

        cyclicBarrierA.await();

    }

    private static void test1() {
        AtomicInteger count2 = new AtomicInteger(0);

        CyclicBarrier cyclicBarrier = new CyclicBarrier(10, () -> {
            System.out.println("多线程循环完成" + count2.getAndIncrement());
        });
        for (int i = 0; i < 100; i++) {
            new Thread(new Task(cyclicBarrier)).start();
        }
    }

    static class Task implements Runnable {
        private final CyclicBarrier cyclicBarrier;

        public Task(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                System.out.println(count.getAndIncrement());
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }


}
