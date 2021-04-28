package io.github.syske.java.learning.thread;

import java.util.concurrent.*;

/**
 * @program: java-learning
 * @description: 多线程测试
 * @author: syske
 * @create: 2021-03-20 21:11
 */
class MyCallableTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<String> callable = () -> "hello Callable";
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<String> submit = executorService.submit(callable);
        System.out.println(submit.get());
//        executorService.shutdown();
    }
}
