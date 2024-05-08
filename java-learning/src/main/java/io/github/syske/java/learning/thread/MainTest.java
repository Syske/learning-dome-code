package io.github.syske.java.learning.thread;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author syske
 * @date 2021-04-22 15:47
 */
public class MainTest {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
//        for (int i = 0; i < 100000; i++) {
//            System.out.println("str" + i);
//        }
//        long entTime = System.currentTimeMillis();
//        System.out.println("耗时：" + (entTime - startTime));

        int i = 2;
        System.out.println(i--);
        System.out.println(--i);
        long l = 3L;
        l--;
        byte k = 4;
        k--;
        short j = 5;
        j--;
        double h = 6d;
        h--;
        float f = 7f;
        f --;
        System.out.println();
        for (int m = 0; m < 10; m++) {

            printAbc();
        }
    }

    private static void testInput() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String next = scanner.next();
            if ("q".equals(next)) {
                System.out.println("exit");
                System.exit(0);
            }
            System.out.println(next);
        }
    }

    private static void test() throws Exception {
        new FileNotFoundException();
        new NoSuchMethodException();
        new ClassCastException();
        new NullPointerException();
        new IndexOutOfBoundsException();

        int i = 12312312;

    }
    static final AtomicReference<Character> lock = new AtomicReference<>('a');
    private static void printAbc() {

        Runnable a = () -> {
            synchronized (lock) {
                if (lock.get().equals('a')) {
                    System.out.print("A");
                    lock.set('b');
                }
            }
        };
        Runnable b = () -> {
            synchronized (lock) {
                if (lock.get().equals('b')) {
                    System.out.print("B");
                    lock.set('c');
                }
            }
        };
        Runnable c = () -> {
            synchronized (lock) {
                if (lock.get().equals('c')) {
                    System.out.print("C");
                    lock.set('a');
                }
            }
        };

        new Thread(a).start();
        new Thread(b).start();
        new Thread(c).start();
    }
}
