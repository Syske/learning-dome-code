package io.github.syske.java.learning.thread;

/**
 * @program: java-learning
 * @description: 技术
 * @author: syske
 * @create: 2021-03-03 19:20
 */
public class OddTest {
    public static void main(String[] args) {
        System.out.println("isOdd1:" + isOdd1(5));
        System.out.println("isOdd1:" + isOdd1(6));
        System.out.println("isOdd2:" + isOdd2(5));
        System.out.println("isOdd2:" + isOdd2(6));
        System.out.println("isOdd3:" + isOdd3(5));
        System.out.println("isOdd3:" + isOdd3(6));
        System.out.println("isOdd4:" + isOdd4(5));
        System.out.println("isOdd4:" + isOdd4(6));
        System.out.println(1 >> 2 << 2);
        System.out.println(2 << 1);
    }

    /**
     * 初学者
     *
     * @param i
     * @return
     */
    public static boolean isOdd1(int i) {
        if (i%2 == 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 普通写法
     * @param i
     * @return
     */
    public static boolean isOdd2(int i) {
        return i%2 == 1;
    }

    /**
     * 普通写法：考虑负数的情况
     * @param i
     * @return
     */
    public static boolean isOdd2_1(int i) {
        return i%2 == 1 || i%2 == -1;
    }

    public static boolean isOdd(int i) {
        return i % 2 != 0;
    }

    /**
     * 普通写法：考虑负数的情况，优化后
     * @param i
     * @return
     */
    public static boolean isOdd2_2(int i) {
        return i%2 != 0;
    }

    /**
     * 高端写法
     * @param i
     * @return
     */
    public static boolean isOdd3(int i) {
        return (i & 1) == 1;
    }

    public static boolean isOdd4(int i) {
        return i >> 1 << 1 != i;
    }
}
