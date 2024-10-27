package io.github.syske.leetcode;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 给你一个字符串 s 和一个字符串列表 wordDict 作为字典。请你判断是否可以利用字典中出现的单词拼接出 s 。
 注意：不要求字典中出现的单词全部都使用，并且字典中的单词可以重复使用。

 示例 1：
 输入: s = "leetcode", wordDict = ["leet", "code"]
 输出: true
 解释: 返回 true 因为 "leetcode" 可以由 "leet" 和 "code" 拼接成。
 示例 2：
 输入: s = "applepenapple", wordDict = ["apple", "pen"]
 输出: true
 解释: 返回 true 因为 "applepenapple" 可以由 "apple" "pen" "apple" 拼接成。
 示例 3：
 输入: s = "catsandog", wordDict = ["cats", "dog", "sand", "and", "cat"]
 输出: false
 示例 4：
 输入: s = "catsandogandapple", wordDict = ["cats", "dog", "sand", "and", "cat","andog", "apple"]
 输出: true
 */
public class TestDemo1 {

    public static void main(String[] args) {
        String s = "aaacabadae";
        String[] wordDict = {"aaa", "ca", "ba","d", "ae"};
        Boolean b = canConcat2(s, wordDict);
        System.out.println(b);
    }

    private static Boolean canConcat2(String target, String[] wordDict) {
        if (target == null) {
            return Boolean.FALSE;
        }
        // 将字典转换为集合，便于快速查找
        Set<String> wordSet = new HashSet<>(Arrays.asList(wordDict));
        boolean[] dp = new boolean[target.length() + 1];
        dp[0] = true; // 空字符串总是可以被拼接成
        for (int i = 1; i <= target.length(); i++) {
            for (int j = 0; j < i; j++) {
                // 如果 dp[j] 为 true 且 s.substring(j, i) 在字典中
                if (dp[j] && wordSet.contains(target.substring(j, i))) {
                    dp[i] = true;
                    break; // 找到一种方式即可，无需继续检查
                }
            }
        }
        return dp[target.length()];
    }

    private static Boolean canConcat(String target, String[] dict) {
        if (target == null) {
            return Boolean.FALSE;
        }
        List<String> collect = Arrays.asList(dict);
        collect.sort(Comparator.comparing(String::length).reversed());
        System.out.println(collect);
        for (String s : collect) {
            if (target.contains(s)) {
                target = target.replaceAll(s, "");
            }
            if (target.isEmpty()) {
                return true;
            }
        }

        return Boolean.FALSE;
    }
}
