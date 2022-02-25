/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.thread;

/**
 * 线程状态测试
 *
 * @author syske
 * @version 1.0
 * @date 2021-11-30 8:34
 */
public class ThreadStatus {
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep(50000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
