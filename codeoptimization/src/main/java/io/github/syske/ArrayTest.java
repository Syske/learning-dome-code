package io.github.syske;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: codeoptimization
 * @description: 数组相关测试
 * @author: syske
 * @create: 2020-02-21 09:41
 */
public class ArrayTest {
    public static void main(String[] args) {

        intTest();
    }

    private static void intTest() {
        int size = 1000000;
        int[] ints = new int[size];
        List<Integer> intList = new ArrayList<>(size);

        // 数组初始化

        long startTime0 = System.currentTimeMillis();
        for (int i = size-1; i >= 0; i--) {
            ints[i] =  i;
        }
        long endTime0 = System.currentTimeMillis();



        // List初始化
        long startTime1 = System.currentTimeMillis();
        for (int i = size-1; i >= 0; i--) {
            intList.add(i);
        }
        long endTime1 = System.currentTimeMillis();



        long startTime2 = System.currentTimeMillis();
        int sum1 = 0;

        for (int i = 0; i < size; i++) {
            sum1 += ints[i%10];
        }
        long endTime2 = System.currentTimeMillis();



        // List初始化
        long startTime3 = System.currentTimeMillis();
        int sum2 = 0;
        for (int i = 0; i < size; i++) {
            sum2 += intList.get(i%10);
        }
        long endTime3 = System.currentTimeMillis();
        System.out.println("array 初始化 用时(ms)：" + (endTime0- startTime0));
        System.out.println("sum1:" + sum1);
        System.out.println("list 初始化 用时(ms)：" + (endTime1- startTime1));
        System.out.println("sum2:" + sum2);
        System.out.println("array 遍历 用时(ms)：" + (endTime2- startTime2));
        System.out.println("list 遍历 用时(ms)：" + (endTime3- startTime3));
    }

    private static void stringTest() {
        int size = 1000000;
        String[] stringArray = new String[size];
        List<String > stringList = new ArrayList<>(size);

        // 数组初始化
        String content = "数组测试字符串";
        long startTime0 = System.currentTimeMillis();
        for (int i = size-1; i >= 0; i--) {
            stringArray[i] = content + i;
        }
        long endTime0 = System.currentTimeMillis();



        // List初始化
        long startTime1 = System.currentTimeMillis();
        for (int i = size-1; i >= 0; i--) {
            stringList.add(content + i);
        }
        long endTime1 = System.currentTimeMillis();



        long startTime2 = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            System.out.println(stringArray[i]);
        }
        long endTime2 = System.currentTimeMillis();



        // List初始化
        long startTime3 = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            System.out.println(stringList.get(i));
        }
        long endTime3 = System.currentTimeMillis();
        System.out.println("array 初始化 用时(ms)：" + (endTime0- startTime0));
        System.out.println("list size:" + stringList.size());
        System.out.println("list 初始化 用时(ms)：" + (endTime1- startTime1));
        System.out.println("list size:" + stringList.size());
        System.out.println("array 遍历 用时(ms)：" + (endTime2- startTime2));
        System.out.println("list 遍历 用时(ms)：" + (endTime3- startTime3));
    }
}
