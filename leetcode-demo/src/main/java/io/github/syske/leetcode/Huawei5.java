package io.github.syske.leetcode;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @program: leetcode-demo
 * @description:
 * @author: syske
 * @create: 2020-11-11 18:19
 */
public class Huawei5 {


    public static void main(String[] args) throws Exception {
        BufferedReader bf = new BufferedReader(
                new InputStreamReader(System.in));
        String s = null;
        StringBuilder sb = new StringBuilder();
        while ((s = bf.readLine()) != null) {
            sb.append(s);

            int length = sb.length();
            sb.deleteCharAt(length - 1);
            if (length >= 8) {
                while (length >= 8) {
                    System.out.println(sb.substring(0, 8));
                    sb.delete(0, 8);
                    length = sb.length();
                }
                if (length > 0 && length < 8) {
                    sb.append("00000000");
                    System.out.println(sb.substring(0, 8));
                    sb.delete(0, length);
                }

            } else {
                sb.append("00000000");
                System.out.println(sb.substring(0, 8));
                sb.delete(0, length);
            }

            /*int lastCount = length % 8;
            if (length == 8) {
                sb.append(s);
                sb.append("\n");
            } else if (length <= 8) {
                sb.append(s);
                for (int i = 0; i < 8 - lastCount; i++) {
                    sb.append("0");
                }
                sb.append("\n");
            } else if (length > 8) {
                int count = length / 8;
                for (int i = 0; i < count; i++) {
                    sb.append(s.substring(i * 8, i * 8 + 8)).append("\n");
                }
                if (lastCount < 8) {
                    String substring = s.substring(length - lastCount, length);
                    sb.append(substring);
                }
                for (int i = 0; i < 8 - lastCount; i++) {
                    sb.append("0");
                }
                sb.append("\n");
            }*/
        }
        //sb.deleteCharAt(sb.length() - 1);
        //System.out.println(sb.toString());
    }

}
