package io.github.syske.leetcode;

import java.util.Scanner;

public class Solution0603 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNextInt()) { // 注意 while 处理多个 case
            int n = in.nextInt();
            int[] gems = new int[n];
            for (int i = 0; i < n; i++) {
                int price = in.nextInt();
                gems[i] = price;
            }
            int val = in.nextInt();
            int sum = 0;
            int count = 0;
            int maxCount = 0;
            for (int i = 0; i < n; i++) {
                sum = gems[i];
                if (sum >= val) {
                    continue;
                }
                for (int j = i + 1; j < n; j++) {
                    if (sum + gems[j] >= val) {
                        count = j - i;
                        if (sum + gems[j] == val) {
                            count ++;
                        }
                        break;
                    } else {
                        sum += gems[j];
                    }
                }
                if (count > maxCount) {
                    maxCount = count;
                }
            }
            System.out.println(maxCount);
        }
    }
}
