package io.github.syske.leetcode;

import java.util.*;

/**
 * @author syske
 * @date 2022/9/19 9:20
 */
public class Solution0919 {
    /**
     * 最大前缀
     *
     * @param strs
     * @return
     */
    public String longestCommonPrefix(String[] strs) {
        String result = "";
        StringBuilder s = new StringBuilder(strs[0]);
        for (int i = strs[0].length(); i > 0; i--) {
            int count = 0;
            for (String str : strs) {
                if (str.startsWith(s.toString())) {
                    count++;
                } else {
                    break;
                }
            }
            if (count == strs.length) {
                result = s.toString();
                break;
            }
            s.deleteCharAt(s.length() - 1);
        }
        return result;
    }

    public int[] frequencySort(int[] nums) {
        return null;
    }

    /**
     * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
     *
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        int end = 0;
        int maxIndex = 0;
        for (int i = 0; i < s.length(); i++) {
            int begin = i;
            for (int j = i + 1; j < s.length(); j++) {
                end = j;
                if (s.toCharArray()[i] == s.toCharArray()[j]) {
                    break;
                }
            }
            if (maxIndex < end - begin) {
                maxIndex = end - begin;
            }
        }
        return maxIndex;
    }

    static HashMap<Character, Character> VALUE_DIR = new HashMap<>();

    static {
        VALUE_DIR.put(')', '(');
        VALUE_DIR.put(']', '[');
        VALUE_DIR.put('}', '{');
    }


    public boolean isValid(String s) {
        char[] chars = s.toCharArray();
        if (chars.length % 2 != 0) {
            return false;
        }
        Stack<Character> characters = new Stack<>();

        for (int i = 0; i < chars.length; i++) {
            if (VALUE_DIR.containsKey(chars[i])) {
                if (characters.isEmpty()) {
                    return false;
                }
                if (!VALUE_DIR.get(chars[i]).equals(characters.peek())) {
                    return false;
                } else {
                    characters.pop();
                }
            } else {
                characters.add(chars[i]);
            }
        }

        return characters.empty();
    }

    public boolean canFormArray(int[] arr, int[][] pieces) {
        HashMap<Integer, int[]> intDic = new HashMap<>();
        for (int[] piece : pieces) {
            intDic.put(piece[0], piece);
        }

        int count = 0;
        for (int i = 0; i < arr.length && count < arr.length; i++) {
            int[] ints = intDic.get(arr[count]);
            if (Objects.nonNull(ints)) {
                for (int j = 0; j < ints.length; j++) {
                    if (ints[j] == arr[count]) {
                        count++;
                    } else {
                        return false;
                    }

                }
            }
        }
        return count == arr.length;
    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        double midNums1, midNums2;
        int mideNums = (nums1.length + nums2.length)/2;
        int midIndex1 = nums1.length/2;
        if (nums1.length % 2 == 0) {
            midNums1 = (double) (nums1[nums1.length/2 - 1] + nums1[nums1.length/2])/2;
        } else {
            midNums1 = nums1[nums1.length/2];
        }

        int midIndex2 = nums1.length/2;
        if (nums2.length % 2 == 0) {
            midNums2 = (double) (nums2[nums2.length/2 - 1] + nums2[nums2.length/2])/2;
        } else {
            midNums2 = nums2[nums2.length/2];
        }
        if (midNums1 > midNums2) {

        }
        return 0;
    }

    public static void main(String[] args) {
        String[] strs = {"dog", "racecar", "car"};
        int[] arr = {1, 3};
        int[] pieces = {2};
        System.out.println(new Solution0919().findMedianSortedArrays(arr, pieces));
    }
}
