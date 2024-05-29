package io.github.syske.java.learning.thread;

import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ThreadPoolTet {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int corePoolSize = 10;
        int maximumPoolSize = 20;
        long keepAliveTime = 10;
        TimeUnit unit = TimeUnit.MICROSECONDS;
        BlockingDeque<Runnable> workQueue = new LinkedBlockingDeque<>(20);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        for (int i = 0; i < 40; i++) {
            System.out.println(i + " # " + threadPoolExecutor);
            threadPoolExecutor.execute(() -> {
                String name = Thread.currentThread().getName();
                System.out.println("hello threadPool: "+ name);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        List<CompletableFuture<Integer>> callableList = new ArrayList<>();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            CompletableFuture<Integer> integerCompletableFuture = CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return finalI;
            });
            callableList.add(integerCompletableFuture);

        }
        System.out.println("before:" + stopWatch);
        for (CompletableFuture<Integer> integerCompletableFuture : callableList) {
            System.out.println(integerCompletableFuture.get());
        }
        stopWatch.stop();
        System.out.println(stopWatch);
        threadPoolExecutor.shutdown();
    }
}



