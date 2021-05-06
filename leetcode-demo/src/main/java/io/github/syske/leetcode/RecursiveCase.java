package io.github.syske.leetcode;

import org.junit.Test;

/**
 * 递归算法
 */
public class RecursiveCase {

    public static void printOut(int n) {
        if (n >= 10) {
            printOut(n / 10);
            printDigit(n % 10);
        }
    }

    public static void printDigit(int n) {
        System.out.println(n);
    }


    @Test
    public void testPrintOut() {
        printOut(82546);
    }
}
