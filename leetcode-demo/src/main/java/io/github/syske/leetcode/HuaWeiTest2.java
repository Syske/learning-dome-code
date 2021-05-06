package io.github.syske.leetcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @program: leetcode-demo
 * @description:
 * @author: syske
 * @create: 2020-11-04 16:17
 */
public class HuaWeiTest2 {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String input = null;
        while ((input = bf.readLine()) != null) {
            String[] strings = input.split(" ");
            StringBuilder tmpStrBuilder = new StringBuilder();
            for (int i = strings.length; i > 0; i--) {
                if (i == 0) {
                    tmpStrBuilder.append(strings[i]);
                } else {
                    tmpStrBuilder.append(strings[i] + " ");
                }
            }
            System.out.println(tmpStrBuilder.toString());
        }
    }
}
