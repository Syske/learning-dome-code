/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.springboottokenbucketlimit.token;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.ExecutorService;

/**
 * @author syske
 * @version 1.0
 * @date 2021-11-02 22:48
 */
public class TokenBucket4 {
    private volatile int token;
    private final int capacity;

    private static Unsafe unsafe = null;
    private static final long valueOffset;
    private final Object lock = new Object();
    private final int rate;

    static {
        try {
            // 应用开发中使用unsafe对象必须通过反射获取
            Class<?> clazz = Unsafe.class;
            Field f = clazz.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            unsafe = (Unsafe) f.get(clazz);
            valueOffset = unsafe.objectFieldOffset(TokenBucket4.class.getDeclaredField("token"));
        } catch (Exception ex) {throw new Error(ex);}
    }

    public TokenBucket4(int capacity, int rate){
        this.capacity = capacity;
        this.token = capacity;
        this.rate = rate;
    }

    /**
     * 获取一个令牌
     */
    public boolean acquire(){
        int current = token;
        if(current<=0){
            // 保证在token已经用光的情况下依然有获取竞争的能力
            current = capacity;
        }

        long expect = rate;// max wait 1s
        long future = System.currentTimeMillis()+expect;
        while(current>0){
            if(compareAndSet(current, current-1)){
                return true;
            }
            current = token;
            if(current<=0 && expect>0){
                // 在有效期内等待通知
                synchronized (lock){
                    try {
                        lock.wait(expect);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                current = token;
                if(current<=0){
                    current = capacity;
                }
                expect = future - System.currentTimeMillis();
            }
        }
        return false;
    }

    private boolean compareAndSet(int expect, int update) {
        return unsafe.compareAndSwapInt(this, valueOffset, expect, update);
    }

    /**
     * 补充令牌
     */
    public void supplement(final ExecutorService executorService){
        this.token = capacity;
        executorService.execute(() -> {
            synchronized (lock){
                lock.notifyAll();
            }
        });
    }

    public static void main(String[] args) throws InterruptedException {
        TokenBucket4 tokenBucket4 = new TokenBucket4(10, 50);
        for (int i = 0; i < 100; i++) {
            boolean acquire = tokenBucket4.acquire();
            System.out.printf("timestamp:%s, hello，result: %s\n", System.currentTimeMillis(), acquire);
            Thread.sleep(70L);
        }
    }
}
