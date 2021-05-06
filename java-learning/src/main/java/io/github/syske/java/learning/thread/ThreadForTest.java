package io.github.syske.java.learning.thread;

import org.junit.Test;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author syske
 * @date 2021-04-18 21:06
 */
public class ThreadForTest {
    private ThreadPoolExecutor threadPoolExecutor =
            new ThreadPoolExecutor(5, 10, 1,
                    TimeUnit.SECONDS, new LinkedBlockingQueue<>(5));

    ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();


    {
        threadPoolTaskExecutor.initialize();
        threadPoolTaskExecutor.setKeepAliveSeconds(1);
        threadPoolTaskExecutor.setMaxPoolSize(10);
        threadPoolTaskExecutor.setCorePoolSize(5);
    }
    @Test
    public void test() {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i <100000 ; i++) {
            final int finalI = i;
            threadPoolTaskExecutor.submit(() -> {
                try {
                    if (finalI == 520) {
                        System.out.println(finalI / 0);
                    }
                    Thread.sleep(3000L);
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                    System.out.println("报错了" + e);
                    System.out.println("事务回滚");

                }
                System.out.println(finalI);
            });
        }
        System.out.println(" 事务提交");
        long endTime = System.currentTimeMillis();
        System.out.println("耗时：" + (endTime - startTime));
    }

    @Test
    public void test2() {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i <100000 ; i++) {
                System.out.println(i);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("耗时：" + (endTime - startTime));
    }
}
