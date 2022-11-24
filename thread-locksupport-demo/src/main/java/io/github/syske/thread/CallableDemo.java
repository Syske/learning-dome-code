/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author syske
 * @version 1.0
 * @date 2021-11-09 22:16
 */
public class CallableDemo implements Callable<String> {
    @Override
    public String call() throws Exception {
        return "hello callable";
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Future<String> submit = Executors.newSingleThreadExecutor().submit(new CallableDemo());
        String s = submit.get();
        System.out.println(s);
    }
}
