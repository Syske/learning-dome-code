/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.springboottokenbucketlimit.token;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

/**
 * @author syske
 * @version 1.0
 * @date 2021-11-02 22:06
 */
public class TokenBucket3 {
    public static void sleepUninterruptibly(long sleepFor, TimeUnit unit) {
        boolean interrupted = false;
        try {
            long remainingNanos = unit.toNanos(sleepFor);
            long end = System.nanoTime() + remainingNanos;
            while (true) {
                try {
                    // TimeUnit.sleep() treats negative timeouts just like zero.
                    NANOSECONDS.sleep(remainingNanos);
                    return;
                } catch (InterruptedException e) {
                    interrupted = true;
                    remainingNanos = end - System.nanoTime();
                }
            }
        } finally {
            if (interrupted) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {

        for (int i = 0; i < 100; i++) {
            sleepUninterruptibly(100L, TimeUnit.MILLISECONDS);
            System.out.printf("timestamp:%s, hello\n", System.currentTimeMillis());
        }
    }
}
