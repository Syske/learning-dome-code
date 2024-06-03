package io.github.syske.leetcode;

import java.util.Scanner;

public class Solution06032 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNextLine()) { // 注意 while 处理多个 case
            String[] lCount = in.nextLine().split(" ");
            String[] lA  = in.nextLine().split(" ");
            String[] lB  = in.nextLine().split(" ");
            int countA = Integer.valueOf(lCount[0]);
            int countB = Integer.valueOf(lCount[1]);
            int tranA = 0;
            String temp = "0";
            int[] sumBs = new int[countB];
            int[] sumAs = new int[countA];
            for (int i = 0; i < countA; i ++) {
                int sumB = 0;
                for(int j = 0; j < countB; j++) {
                    temp = lB[j];
                    lB[j] = lA[i];
                    lA[i] = temp;
                    sumB += Integer.valueOf(lB[j]);
                }
                sumBs[i] = sumB;
            }

            for (int i = 0; i < countB; i ++) {
                int sumA = 0;
                for(int j = 0; j < countA; j++) {
                    temp = lA[j];
                    lA[j] = lB[i];
                    lB[i] = temp;
                    sumA += Integer.valueOf(lA[j]);
                }
                sumAs[i] = sumA;
            }

            for (int i = 0 ; i < countB; i++) {
                for (int j = 0; j < countB; j++) {
                    if (sumAs[i] == sumBs[j]) {
                        System.out.println(String.format("%s %s", lA[j], lB[i]));
                        break;
                    }
                }
            }

        }
    }
}
