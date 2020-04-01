package io.github.syske;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @program: codeoptimization
 * @description: 如下代码是为了测试 Collocation.contains()方法的性能，比较过程中发现list用时不稳定，set用时稳定,且性能好
 * 频繁添加元素时，list优于set，且指定list的大小，有助于大大提示添加效率，但set在指定大小后，反而性能下降
 * @author: syske
 * @create: 2019-12-13 17:54
 */
public class CollectionTest {
    public static void main(String[] args) {
        int size = 100000;
        ArrayList<String> list = new ArrayList<>();
        long startTime0 = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            list.add("addTest" + i);
        }
        long endTime0 = System.currentTimeMillis();
        System.out.println("list 增加元素 用时(ms)：" + (endTime0- startTime0));
        long startTime = System.currentTimeMillis();
        if (list.contains("addTest99999999")) {
            System.out.println("addTest9999 list");
        }
        long endTime = System.currentTimeMillis();
        System.out.println("list contains 用时(ms)：" + (endTime- startTime));

        Set<String> list2 = new HashSet<>();
        long startTime1 = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            list2.add("addTest" + i);
        }
        long endTime1 = System.currentTimeMillis();
        System.out.println("set 增加元素 用时(ms)：" + (endTime1- startTime1));
        long startTime2 = System.currentTimeMillis();
        if (list2.contains("addTest99999999")) {
            System.out.println("addTest9999 set");
        }
        long endTime2 = System.currentTimeMillis();
        System.out.println("set contains 用时(ms)：" + (endTime2- startTime2));
    }
}
