package io.github.syske.leetcode;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Solution060303 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNextLine()) { // 注意 while 处理多个 case
            int n = Integer.valueOf(in.nextLine());
            int count = 0;
            Set<Integer> hasTask = new HashSet<>();
            for (int i = 0; i < n; i++) {
                String[] tasks = in.nextLine().split(" ");
                int begin = Integer.valueOf(tasks[0]);
                int end = Integer.valueOf(tasks[1]);
                if (hasTask.contains(begin)) {
                    for (int j = begin; j <= end; j++) {
                        if (!hasTask.contains(j)) {
                            hasTask.add(j);
                            count++;
                            break;
                        }
                    }
                } else {
                    count++;
                    hasTask.add(begin);
                }
            }
            System.out.println(count);
        }
    }
}
