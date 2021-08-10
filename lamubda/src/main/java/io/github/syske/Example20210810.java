/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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

    }

    public static void groupByTest() {
        List<TestVo> testVoList = Lists.newArrayList();
//        for (long i = 0; i < 10; i++) {
//            if (i < 3) {
                testVoList.add(new TestVo(1L, 1,111L, 10L, "test1" ));
                testVoList.add(new TestVo(2L, 2,111L, 20L, "test2" ));
                testVoList.add(new TestVo(3L, 3,111L, 30L, "test3" ));
//            }

//            if (i > 3 && i < 6) {
                testVoList.add(new TestVo(4L, 1,112L, 10L, "test4"));
                testVoList.add(new TestVo(5L, 2,112L, 20L, "test5"));
                testVoList.add(new TestVo(6L, 3,112L, 30L, "test6"));
//            }

//            if (i > 6 && i < 10) {
                testVoList.add(new TestVo(7L, 1, 113L,10L, "test7"));
                testVoList.add(new TestVo(8L, 2, 113L,20L, "test8"));
                testVoList.add(new TestVo(9L, 3, 113L,30L, "test9"));
                testVoList.add(new TestVo(10L, 4, 113L,40L, "test10"));
//            }
//        }

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

    private static void parallelStreamTest() {
        List<Integer> integers = Lists.newArrayList();
        Random random = new Random(100);
        for (int i = 0; i < 100; i++) {
            integers.add(random.nextInt(100));
        }
        AtomicInteger index = new AtomicInteger(0);
        // 小于十
        AtomicInteger sizeOf10 = new AtomicInteger(0);
        // 大于十，小于50
        AtomicInteger sizeOf50 = new AtomicInteger(0);
        // 大于50，小于100
        AtomicInteger sizeOf100 = new AtomicInteger(0);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Integer> integersList = Lists.newArrayList();
        for (int j = 0; j < 5; j++) {
            executorService.execute(() -> {
                System.out.println("原始数据：" + integers);
                integers.parallelStream().forEach(i -> {
                    index.incrementAndGet();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(index.get());
                    integersList.add(i);
                });
                System.out.println("sizeOf10：" + sizeOf10);
                System.out.println("sizeOf50：" + sizeOf50);
                System.out.println("sizeOf100：" + sizeOf100);
                System.out.println("处理完成后：integers = " + integers);
                System.out.println("处理完成后：integerList" + integersList);

            });
        }

        executorService.shutdown();
    }
}
