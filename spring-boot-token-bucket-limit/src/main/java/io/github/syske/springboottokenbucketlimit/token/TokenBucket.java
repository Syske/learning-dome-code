/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.springboottokenbucketlimit.token;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

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
        executor = new ThreadPoolExecutor(tokenRate, tokenRate, 1L, TimeUnit.SECONDS, new LinkedBlockingDeque<>(0), (r, executor) -> logger.warn("令牌桶已满，请求将被抛弃，size = {}", executor.getPoolSize()));
    }

    public boolean acquire() {
        if (leftTokenCount.get() == 0) {
            lastTimestamp.set(System.currentTimeMillis());
            leftTokenCount.getAndIncrement();
        }

        return Boolean.FALSE;
    }


}
