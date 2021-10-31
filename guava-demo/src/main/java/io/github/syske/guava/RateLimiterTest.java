/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.guava;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.RateLimiter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author syske
 * @version 1.0
 * @date 2021-10-28 21:41
 */
public class RateLimiterTest {
    public static void main(String[] args) {
        final RateLimiter rateLimiter = RateLimiter.create(4.0);
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        List<Runnable> tasks = Lists.newArrayList();
        for (int i = 0; i < 100; i++) {
            rateLimiter.acquire();
            executorService.execute(() -> {
                String dateTime = new SimpleDateFormat("HH:mm:ss:SSS").format(new Date());
                System.out.printf("limiter-%s%n", dateTime);
            });
        }
        executorService.shutdown();
    }
}
