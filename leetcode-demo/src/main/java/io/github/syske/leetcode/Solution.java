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

    /**
     * 数字反转
     *
     * @param x
     * @return
     */
    public int reverse(int x) {
        String numStr = Integer.toString(x);
        if (x == 0) {
            return 0;
        }
        try {
            if (x < 0) {
                return Integer.parseInt(reverse(numStr));
            }

            return Integer.parseInt(reverse(numStr));


        } catch (NumberFormatException e) {
            return 0;
        }

    }

    private String reverse(String str) {
        if (str.startsWith("-")) {
            return "-" + new StringBuffer(str.substring(1)).reverse().toString();
        }
        return new StringBuffer(str).reverse().toString();
    }

    /**
     * 回文数
     *
     * @param x
     * @return
     */
    public boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }
        String xStr = Integer.toString(x);
        return reverse(xStr).equals(xStr);
    }

    /**
     * 路径总和
     * 5
     * / \
     * 4   8
     * /   / \
     * 11  13  4
     * /  \      \
     * 7    2      1
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/path-sum
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     *
     * @param root
     * @param sum
     * @return
     */
    public boolean hasPathSum(TreeNode root, int sum) {
        return sumNode(root) == sum;
    }

    private int sumNode(TreeNode root) {
        if (root.left != null && root.right == null) {
            return sumNode(root.left) + root.val;
        }
        if (root.right != null && root.left == null) {
            return sumNode(root.right) + root.val;
        }
        if (root.left != null && root.right != null) {
            return sumNode(root.left) + root.val;
        }
        return root.val;
    }

    /**
     * 最长公共前缀
     *
     * @param strs
     * @return
     */
    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        String str = strs[0];
        if (strs.length == 1) {
            return str;
        }
        String[] strs1 = Arrays.copyOfRange(strs, 1, strs.length);
        for (int i = str.length(); i >= 1; i--) {
            String substring = str.substring(0, i);
            if (equalAllStr(substring, strs1)) {
                return substring;
            }
        }
        return "";
    }

    private boolean equalAllStr(String preFix, String[] strs) {
        //for (String str : strs) {
        for (int i = 0; i < strs.length; i++) {
            if (!strs[i].startsWith(preFix)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 恢复空格
     *
     * @param dictionary
     * @param sentence
     * @return
     */
    public int respace(String[] dictionary, String sentence) {
        StringBuilder tmpStr = new StringBuilder(sentence);

        for (int i = 0; i < dictionary.length; i++) {
            int indexOf = tmpStr.indexOf(dictionary[i]);
            while (indexOf > 0) {
                indexOf = tmpStr.indexOf(dictionary[i]);
                if (indexOf > 0) {
                    String str = dictionary[i];
                    tmpStr.delete(indexOf, indexOf + str.length());
                }

               /* if(tmpStr.charAt(indexOf - 1) == ' ') {
                    tmpStr.replace(indexOf, indexOf + str.length(), str+" ");
                } else {
                    tmpStr.replace(indexOf, indexOf + str.length(), " "+str+" ");
//                tmpStr.insert(indexOf, " ");
//                tmpStr.insert(indexOf +1  + str.length(), str);
//                tmpStr.insert(indexOf +1 + str.length(), " ");
                    System.out.println(tmpStr);
                }*/

            }
        }
        System.out.println(tmpStr.toString());
        return tmpStr.length();
    }

    public int strStr(String haystack, String needle) {
        int length = needle.length();
        if (needle == null || needle == "" ||
                needle == " " || length == 0
                || needle.equals(haystack)) {
            return 0;
        }

        if (haystack.length() < length) {
            return -1;
        }


        for (int i = 0; i < haystack.length(); i++) {
            if (haystack.charAt(i) == needle.charAt(0) && haystack.length() >= i + length &&
                    haystack.substring(i, i + length).equals(needle)) {
                return i;
            }
        }
        return -1;
    }

    public boolean isValid(String s) {
       /* int x = s.indexOf("(");
        if (x > -1) {

            int j = s.indexOf(")");
            String substring = x > -1 && j > -1 && x < j ? s.substring(x, j) : "";
            return !(substring.contains("{") || substring.contains("}")
                    || substring.contains("[") || substring.contains("]")) && !"".equals(substring);
        }
        int y = s.indexOf("{");
        if (y > -1) {
            int j = s.indexOf("}");
            String substring = x > -1 && j > -1 && x < j ? s.substring(y, j) : "";
            return j > -1 && y < j &&
                    !(substring.contains("(") || substring.contains(")")
                            || substring.contains("[") || substring.contains("]")) && !"".equals(substring);
        }
        int z = s.indexOf("[");
        if (z > -1) {
            int j = s.indexOf("]");
            String substring = x > -1 && j > -1 && x < j ? s.substring(z, j) : "";
            return j > -1 && z < j &&
                    !(substring.contains("(") || substring.contains(")")
                            || substring.contains("{") || substring.contains("}")) && !"".equals(substring);
        }*/
        char[] chars = s.toCharArray();
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("(", ")");
        hashMap.put("{", "}");
        hashMap.put("[", "]");
        for (int i = 0; i < s.length(); i++) {
            //s.indexOf()
            //containsStr(s, s.substring(i , i + 1), , )
        }
        return false;
    }

    private boolean containsStr(String source,String target, int start, int end) {
        return source.substring(start,end ).indexOf(target) > -1;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        //
        // System.out.println(new Solution().reverse(-123));
        //System.out.println(new Solution().isPalindrome(-121));
//        TreeNode root = new TreeNode(5);
//        root.left = new TreeNode(8);
//        root.right = new TreeNode(4);
//        root.right.right = new TreeNode(6);
//        root.right.left = null;
//        System.out.println(solution.sumNode(root));
        // String[] strs = {"potimzz"};
        // String str = "potimzzpotimzz";
        //System.out.println(solution.respace(strs, str));
        //String haystack = "aaa", needle = "aaaa";
        //System.out.println(solution.strStr(haystack, needle));
        String s = "(]";
        System.out.println(solution.isValid(s));

    }
}
