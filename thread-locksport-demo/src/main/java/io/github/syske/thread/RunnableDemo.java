/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.thread;

import java.util.Observable;

/**
 * @author syske
 * @version 1.0
 * @date 2021-11-09 22:11
 */
public class RunnableDemo extends Observable implements Runnable {
    @Override
    public void run() {
        System.out.println("我是继承了Thread，并实现了Runnable的类");
    }

    public static void main(String[] args) {
        new Thread(new RunnableDemo()).start();
    }
}
