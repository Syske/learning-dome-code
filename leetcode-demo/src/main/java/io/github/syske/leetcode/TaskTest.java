package io.github.syske.leetcode;

import java.util.HashMap;
import java.util.TreeMap;

/**
 * 给定一组任务，每个任务都是独立线程执行，任务包含起始时间和停止时间，求完成这些任务线程池最少需要几个线程。
 * 例如：输入[0,3),[1,2) [4,6),[5,7)（结束时刻不计算在内）
 * 输出2
 */
public class TaskTest {

    public static int minThreads(int[][] tasks) {
        // 使用TreeMap来存储事件点，并自动按时间排序
        TreeMap<Integer, Integer> events = new TreeMap<>();

        // 遍历所有任务，为每个任务的开始和结束时间添加到events中
        for (int[] task : tasks) {
            int start = task[0];
            int end = task[1];

            // 开始时间增加一个活跃任务
            events.put(start, events.getOrDefault(start, 0) + 1);
            // 结束时间减少一个活跃任务
            events.put(end, events.getOrDefault(end, 0) - 1);
        }
        System.out.println(events);

        // 当前活跃的任务数
        int activeTasks = 0;
        // 最小线程池大小
        int minThreads = 0;

        // 遍历事件点，更新activeTasks和minThreads
        for (int change : events.values()) {
            activeTasks += change;
            minThreads = Math.max(minThreads, activeTasks);
        }

        return minThreads;
    }

    public static void main(String[] args) {
        int[][] tasks ={{1, 2}, {0, 3},{4, 6},{5, 7}};
        System.out.println("Minimum number of threads: " + minThreads(tasks));
    }

}
