package io.github.syske.java.learning.thread;

/**
 * @author syske
 * @date 2021-04-22 15:47
 */
public class MainTest {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            System.out.println("str" + i);
        }
        long entTime = System.currentTimeMillis();
        System.out.println("耗时：" + (entTime - startTime));
    }
}
