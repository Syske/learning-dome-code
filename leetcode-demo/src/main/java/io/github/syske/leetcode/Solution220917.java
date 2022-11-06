/* Copyright © 2022 syske. All rights reserved. */
package io.github.syske.leetcode;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 算法练习0917
 *
 * @author syske
 * @version 1.0
 * @date 2022-09-17 20:54
 */
public class Solution220917 {
    public int maxLengthBetweenEqualCharacters(String s) {
        if (Objects.isNull(s)) {
            return -1;
        }
        char[] chars = s.toCharArray();
        int maxLength = -1;
        for (int i = 0; i < chars.length; i++) {
            for (int j = i + 1; j < chars.length; j++) {
                if (chars[i] == chars[j]) {
                    if (j - i > maxLength) {
                        maxLength = j - i - 1;
                    }
                }
            }
        }
        return maxLength;
    }

    public int lengthOfLongestSubstring(String s) {
        int maxIndex = 0;
        for (int i = 0; i < s.length(); i++) {
            String substring = s.substring(i, i + 1);
            String[] split = s.split(substring);
            Optional<Integer> collect = Arrays.stream(split).map(String::length).collect(Collectors.maxBy(Integer::compareTo));
            System.out.println( substring + ": " + collect.get());
        }
        return maxIndex;
    }

    public static void main(String[] args) {
        System.out.println(new Solution220917().lengthOfLongestSubstring("abcabcbb"));
    }
}
