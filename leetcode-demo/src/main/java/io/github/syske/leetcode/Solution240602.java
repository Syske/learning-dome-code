package io.github.syske.leetcode;

import java.util.Scanner;

public class Solution240602 {
    /**
     *
     明明的随机数
     明明生成了N个1到500之间的随机整数。请你删去其中重复的数字，即相同的数字只保留一个，把其余相同的数去掉，然后再把这些数从小到大排序，按照排好的顺序输出。

     数据范围：


     1≤n≤1000  ，输入的数字大小满足


     1≤val≤500
     时间限制：C/C++ 1秒，其他语言2秒
     空间限制：C/C++ 32M，其他语言64M
     输入描述：
     第一行先输入随机整数的个数 N 。
     接下来的 N 行每行输入一个整数，代表明明生成的随机数。
     具体格式可以参考下面的"示例"。
     输出描述：
     输出多行，表示输入数据处理后的结果

     示例1
     输入例子：
     3
     2
     2
     1
     输出例子：
     1
     2
     例子说明：
     输入解释：
     第一个数字是3，也即这个小样例的N=3，说明用计算机生成了3个1到500之间的随机整数，接下来每行一个随机数字，共3行，也即这3个随机数字为：
     2
     2
     1
     所以样例的输出为：
     1
     2
     */

    public static void main2(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNextInt()) { // 注意 while 处理多个 case
            int n = in.nextInt();
            int temp;
            int[] result = new int[n];
            for (int i = 0; i < n; i++) {
                int val = in.nextInt();
                result[i] = val;
                if (i < 1) {
                    continue;
                }
                for (int j = i; j > 0; j --) {
                    if (result[j] < result[j - 1]) {
                        temp = result[j - 1];
                        result[j - 1] = result[j];
                        result[j] = temp;
                    } else {
                        break;
                    }
                }
            }
            for (int i = 0; i < n; i++) {
                if (i > 0 && result[i] == result[i - 1]) {
                    continue;
                }
                System.out.println(result[i]);
            }

        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNextLine()) { // 注意 while 处理多个 case
            String n = in.nextLine();
            String number = n.substring(2);
            int data = 0;
            for (int i = 0; i < number.length(); i++) {
                int i1 = transfer2num(number.charAt(i));
                data += i1 * Math.pow(16, number.length() - i -1);
            }
            System.out.println(data);

        }


    }
    private static int transfer2num(char ch) {
        if (ch == 'A') {
            return 10;
        }
        if (ch == 'B') {
            return 11;
        }
        if (ch == 'C') {
            return 12;
        }
        if (ch == 'D') {
            return 13;
        }
        if (ch == 'E') {
            return 14;
        }
        if (ch == 'F') {
            return 15;
        }
        return ch - '0';
    }
}
