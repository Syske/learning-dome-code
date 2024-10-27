package io.github.syske.leetcode;

import java.util.Map;
import java.util.TreeMap;

public class TaskTest2 {
    public static void main(String[] args) {
        int[][] input ={{1, 2}, {0, 3},{4, 6},{5, 7}};
        System.out.println(countThread(input));
    }

    private static int countThread(int[][] input) {
        TreeMap<Integer, Integer> events = new TreeMap<>();
        for (int[] ints : input) {
            int start = ints[0];
            int end = ints[1];
            events.put(start, events.getOrDefault(start, 0) + 1);
            events.put(end, events.getOrDefault(end, 0) - 1);
        }

        int taskActives = 0;
        int minThreads = 0;
        for (Map.Entry<Integer, Integer> entry: events.entrySet()) {
            int value = entry.getValue();
            taskActives += value;
            minThreads = Math.max(minThreads, taskActives);
        }
        return minThreads;
    }
}
