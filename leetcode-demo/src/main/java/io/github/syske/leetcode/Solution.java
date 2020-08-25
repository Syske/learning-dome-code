package io.github.syske.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @program: leetcode-demo
 * @description:
 * @author: syske
 * @create: 2020-07-06 18:07
 */
class Solution {
    public int[] twoSum(int[] nums, int target) {
        int result[] = new int[2];
        /*for(int i = 0; i<nums.length; i++) {
            for(int j = i+1; j<nums.length; j++) {
                if(nums[i] + nums[j] == target) {
                    result[0] = i;
                    result[1] = j;
                }
            }

        }*/


        ArrayList<Integer> list = new ArrayList<Integer>(nums.length);
        for (int i = 0; i<nums.length; i++) {
            Integer value = target - nums[i];
            if(list.contains(value)) {
                 result[1] = i;
                 result[0] = list.indexOf(value);
                return result;
            }
            list.add(nums[i]);
        }

        /*List<Integer> ints = new ArrayList<Integer>();

        for (int i = 0; i<nums.length; i++) {
            Integer value = target - nums[i];
            if(ints.contains(value)) {
                result[1] = i;
                result[0] = ints.indexOf(value);
                return result;
            }

        }*/

        return result;
    }
}