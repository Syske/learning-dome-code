/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.springboottokenbucketlimit.token;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author syske
 * @version 1.0
 * @date 2021-11-02 8:40
 */
public class TokenBucket {

    private Logger logger = LoggerFactory.getLogger(TokenBucket.class);
    /**
     * 令牌桶的容量
     */
    private int capacity = 10;

    /**
     * 剩余token数量
     */
    private AtomicInteger leftTokenCount = new AtomicInteger(0);

    /**
     * 上一个令牌创建时间
     */
    private AtomicLong lastTimestamp;

    /**
     * 休眠时间
     */
    private int sleepMillis;

    /**
     * token生成速率（限流速率）
     */
    private int tokenRate;

    private ThreadPoolExecutor executor;

    public TokenBucket(int capacity, int tokenRate) {
        this.capacity = capacity;
        this.tokenRate = tokenRate;
        sleepMillis = 1000 / tokenRate;
        executor = new ThreadPoolExecutor(tokenRate, tokenRate, 1L, TimeUnit.SECONDS, new LinkedBlockingDeque<>(1),
            (r, executor) -> logger.warn("令牌桶已满，token将被丢弃，size = {}", executor.getPoolSize()));
    }

    public synchronized boolean acquire() {
        createToken();
        return leftTokenCount.get() > 0;
    }

    private void createToken() {
            boolean interrupted = false;
            try {
                leftTokenCount.getAndIncrement();
                long nanos = TimeUnit.MICROSECONDS.toNanos(sleepMillis);
                while (true) {
                    try {
                        TimeUnit.NANOSECONDS.sleep(nanos);
                    } catch (InterruptedException interruptedException) {
                        logger.error("运行异常");
                        interrupted = true;
                    }
                }
            } finally {
                if (interrupted) {
                    Thread.currentThread().interrupt();
                }
            }
    }

    public static void main(String[] args) {
        TokenBucket tokenBucket = new TokenBucket(5, 5);
        for (int i = 0; i < 20; i++) {
            boolean acquire = tokenBucket.acquire();
            System.out.printf("timestamp: %s, result: %s%n", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date()), acquire);
        }
        tokenBucket.executor.shutdown();

    }

}
