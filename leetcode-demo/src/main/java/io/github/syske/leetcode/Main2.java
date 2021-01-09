package io.github.syske.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder stringBuilder = new StringBuilder();
        while(scanner.hasNext()) {
            int numCount = scanner.nextInt();
            //int[] nums = new int[numCount];
            ArrayList<Integer> numList = new ArrayList<>();
            for (int i = 0; i < numCount; i++) {
                int num = scanner.nextInt();
                if (!numList.contains(num)) {
                    numList.add(num);
                }
                //nums[i] = scanner.nextInt();
            }
            Object[] nums = numList.toArray();
            Arrays.sort(nums);
            for (int i = 0; i < nums.length; i++) {
                stringBuilder.append(nums[i]).append("\n");
            }
        }
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        System.out.println(stringBuilder.toString());
    }
}
