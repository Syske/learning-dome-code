/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.springboottokenbucketlimit.token;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author syske
 * @version 1.0
 * @date 2021-11-02 23:01
 */
public class TokenBucket5 {
    private ArrayBlockingQueue<Integer> blockingQueue;
    private int limit;
    private TimeUnit unit;
    private int rate;
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    public TokenBucket5(int limit, TimeUnit unit, int rate) {
        this.blockingQueue = new ArrayBlockingQueue<>(limit);
        this.limit = limit;
        this.unit = unit;
        this.rate = rate;
        init();
        start();
    }

    private void init() {
        for (int i = 0; i < limit; i++) {
            blockingQueue.add(i);
        }
    }

    private void createToken() {
        blockingQueue.offer(1);
    }

    private void start() {
        scheduledExecutorService.scheduleAtFixedRate(this::createToken, rate, rate, unit);
    }

    public boolean acquire() {
        return blockingQueue.poll() == null ? false : true;
    }

    public void release() {
        scheduledExecutorService.shutdown();
    }

    public static void main(String[] args) {
        TokenBucket5 tokenBucket5 = new TokenBucket5(5, TimeUnit.MILLISECONDS, 100);
        try {
            for (int i = 0; i < 100; i++) {
                boolean acquire = tokenBucket5.acquire();
                System.out.printf("timestamp:%s, hello，result: %s\n", System.currentTimeMillis(), acquire);
                Thread.sleep(80L);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            tokenBucket5.release();
        }

    }
}
