package io.github.syske.leetcode;

import java.util.Scanner;

public class RandomFilter {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNextInt()) { // 注意 while 处理多个 case
            int n = in.nextInt();
            int[] randoms = new int[n];
            int temp = 0;
            for (int i = n; i > 0;i--) {
                randoms[n - i] = in.nextInt();

                for (int j = i; j > 0; j--) {
                    if (i - j >= 1) {
                        if (randoms[i - j - 1] == randoms[i - j]) {
                            randoms[i - j] = randoms[i - j - 1];
                            randoms[i - j - 1] = -1;
                        } else if (randoms[i - j - 1] > randoms[i - j]) {
                            temp = randoms[i - j - 1];
                            randoms[i - j - 1] = randoms[i - j];
                            randoms[i - j] = temp;
                        }
                    }

                }
//                    if (randoms[n - i  - 1] == randoms[n - i]) {
//                        randoms[n - i] = randoms[n - i - 1];
//                        randoms[n - i -1] = -1;
//                    } else if (randoms[n - i  - 1] > randoms[n - i]) {
//                        temp = randoms[n - i  - 1];
//                        randoms[n - i  - 1] = randoms[n - i];
//                        randoms[n - i] = temp;
//                    }
            }
            for (int i = 0; i < n;i++) {
                if (randoms[i] != -1) {
                    System.out.println(randoms[i]);
                }
            }
        }
    }
}