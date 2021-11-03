/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.springboottokenbucketlimit.token;

import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.TimeUnit.MICROSECONDS;

/**
 * @author syske
 * @version 1.0
 * @date 2021-11-02 21:35
 */
public class TokenBucket2 {
    /**
     * 令牌生成速率
     */
    private int tokenRate;

    /**
     * 令牌桶容量
     */
    private int capacity;

    /**
     * 上次桶内令牌数更新时间
     */
    private Long lastTimestamp;

    /**
     * 桶内剩余token数
     */
    private AtomicInteger leftTokenCount;

    public TokenBucket2(int tokenRate, int capacity) {
        this.tokenRate = tokenRate;
        this.capacity = capacity;
        this.lastTimestamp = System.nanoTime();
        this.leftTokenCount = new AtomicInteger(capacity);
    }

    private boolean acquire(int token) {
        TokenBucket3.sleepUninterruptibly(100L, MICROSECONDS);
        int currentTokenCount = this.leftTokenCount.get();
        if (token <= this.leftTokenCount.get()) {
            this.leftTokenCount.set(token);
            return true;
        }
        int updateTokenNum = (int) ((System.nanoTime() - this.lastTimestamp) * tokenRate + currentTokenCount);
        if (updateTokenNum < token) {
            return false;
        } else {
            this.lastTimestamp = System.nanoTime();
            this.leftTokenCount.set(updateTokenNum);
        }
        this.leftTokenCount.set(token);
        return true;
    }

    public static void main(String[] args) {
        TokenBucket2 tokenBucket2 = new TokenBucket2(2, 10);
        for (int i = 0; i < 100; i++) {
            boolean acquire = tokenBucket2.acquire(i);
            System.out.printf("timestamp:%s, hello，result: %s\n", System.currentTimeMillis(), acquire);
        }
    }
}
