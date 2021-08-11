/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

/**
 * 2021-08-10
 *
 * @author syske
 * @version 1.0
 * @date 2021-08-10 8:03
 */
public class Example20210810 {
    public static void main(String[] args) {
//        parallelStreamTest();
        groupByTest();
//        parallelStreamTest();

    }

    public static void groupByTest() {
        List<TestVo> testVoList = Lists.newArrayList();
        testVoList.add(new TestVo(1L, 1, 111L, 10L, "test1"));
        testVoList.add(new TestVo(2L, 2, 111L, 20L, "test2"));
        testVoList.add(new TestVo(3L, 3, 111L, 30L, "test3"));

        testVoList.add(new TestVo(4L, 1, 112L, 10L, "test4"));
        testVoList.add(new TestVo(5L, 2, 112L, 20L, "test5"));
        testVoList.add(new TestVo(6L, 3, 112L, 30L, "test6"));

        testVoList.add(new TestVo(7L, 1, 113L, 10L, "test7"));
        testVoList.add(new TestVo(8L, 2, 113L, 20L, "test8"));
        testVoList.add(new TestVo(9L, 3, 113L, 30L, "test9"));
        testVoList.add(new TestVo(10L, 4, 113L, 40L, "test10"));

        System.out.println("初始化后：testVoList = " + testVoList);

        Map<Long, List<TestVo>> typeIdTestVoMap = testVoList.stream().collect(Collectors.groupingBy(TestVo::getTypeId));
        System.out.println("typeIdTestVoMap = " + typeIdTestVoMap);
        Map<String, List<TestVo>> projectNUmSortMap = testVoList.stream().collect(Collectors.groupingBy(t -> String.format("%s.%s", t.getProjectNum(), t.getSort())));
        System.out.println("projectNUmSortMap = " + projectNUmSortMap);
    }

    public static class TestVo {
        Long id;
        Integer sort;
        Long projectNum;
        Long typeId;
        String name;

        public Long getId() {
            return id;
        }

        public Integer getSort() {
            return sort;
        }

        public Long getProjectNum() {
            return projectNum;
        }

        public long getTypeId() {
            return typeId;
        }

        public String getName() {
            return name;
        }

        public TestVo(Long id, Integer sort, Long projectNum, Long typeId, String name) {
            this.id = id;
            this.sort = sort;
            this.projectNum = projectNum;
            this.typeId = typeId;
            this.name = name;
        }

        @Override
        public String toString() {
            return "TestVo{" +
                    "id=" + id +
                    ", typeId=" + typeId +
                    ", name='" + name + '\'' +
                    '}';
        }
    }


    private static void parallelStreamTest2() {
        List<Integer> integers = Lists.newArrayList();
        Random random = new Random(100);
        for (int i = 0; i < 10000; i++) {
            integers.add(random.nextInt(100));
        }
        long start = System.currentTimeMillis();
        List<String> collect1 = integers.parallelStream().map(String::valueOf).collect(Collectors.toList());
        System.out.println("collect1 用时：" + (System.currentTimeMillis() - start));
        long start2 = System.currentTimeMillis();
        List<String> collect2 = integers.stream().map(String::valueOf).collect(Collectors.toList());
        System.out.println("collect2 用时：" + (System.currentTimeMillis() - start2));
        System.out.println("collect1: " + collect1);
        System.out.println("collect2: " + collect2);
    }

    private static void parallelStreamTest() {
        List<Integer> integers = Lists.newArrayList();
        Random random = new Random(100);
        for (int i = 0; i < 10000; i++) {
            integers.add(random.nextInt(100));
        }
        AtomicInteger index = new AtomicInteger(0);
        List<Integer> integersList = Lists.newArrayList();

                System.out.println("原始数据：" + integers.size());
//                integers.stream().forEach(i -> {
                integers.parallelStream().forEach(i -> {
                    index.incrementAndGet();

                    System.out.println(index.get());
                    integersList.add(i);
                });
                System.out.println("处理完成后：integerList.size:" + integersList.size());

    }
}
