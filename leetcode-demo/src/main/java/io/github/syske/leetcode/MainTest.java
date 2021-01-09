package io.github.syske.leetcode;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @program: leetcode-demo
 * @description: 主函数
 * @author: syske
 * @create: 2020-07-06 18:11
 */
public class MainTest {
    public static void main(String[] args) {
       /* Scanner scan = new Scanner(System.in);
        // 判断是否还有输入
        if (scan.hasNextLine()) {
            String str2 = scan.nextLine();
            System.out.println("输入的数据为：" + str2);
        }*/

       int nums[] = {3,2,4};
       int target = 6;
       Solution solution = new Solution();
        System.out.println(Arrays.toString(solution.twoSum(nums, target)));

        char c = 'C';
        System.out.println((char)(c+32));
        int count = 9;
        System.out.println(count/8);
        String ste = "123456789";
        System.out.println(ste.substring(8, ste.length()));
    }
}
