package io.github.syske.leetcode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.Buffer;

public class Main3 {
    public static void main(String[] args) throws Exception {
        BufferedReader bf = new BufferedReader(
                new InputStreamReader(System.in));
        String s = null;
        StringBuilder sb = new StringBuilder();
        while ((s = bf.readLine()) != null) {

            int length = s.length();
            int count = length / 8;
            int lastCount = length % 8;
            if (length == 8) {
                sb.append(s);
                sb.append("\n");
            } else {
                for (int i = 0; i < count; i++) {
                    sb.append(s.substring(i * 8, i * 8 + 8)).append("\n");
                }
                if (lastCount < 8) {
                    if (length > 8) {
                        String substring = s.substring(length - lastCount, length);
                        sb.append(substring);
                    } else {
                        sb.append(s);
                    }

                    for (int i = 0; i < 8 - lastCount; i++) {
                        sb.append("0");
                    }
                    sb.append("\n");
                } else {
                    sb.deleteCharAt(sb.length() - 1);
                }
            }

        }

        System.out.println(sb.toString());
    }
}
