package io.github.syske.leetcode;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @program: leetcode-demo
 * @description:
 * @author: syske
 * @create: 2020-11-05 10:28
 */
public class HuaWeiTest4 {
    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int number = Integer.parseInt(bufferedReader.readLine());
        int s = (int)Math.floor(number/2);
        int count = number%2==1?1:0;
        while(s > 0) {
            if(s%2==1) {
                count++;
            }
            s = (int)Math.floor(s/2);
        }
        System.out.println(count);
    }
}
