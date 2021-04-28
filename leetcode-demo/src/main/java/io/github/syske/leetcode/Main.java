package io.github.syske.leetcode;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws Exception {
        InputStream in = System.in;
        char c = (char) in.read();
        HashMap<Character, Integer> kvs = new HashMap<>();
        while (c != '\n') {
            if (kvs.containsKey(c)) {
                kvs.replace(c, kvs.get(c) + 1);
            } else {
                kvs.put(c, 1);
            }
        }
        char c2 = (char) in.read();
        System.out.println(kvs.get(c2));
        BufferedReader bf = new BufferedReader(new InputStreamReader(in));
        char[] chars = bf.readLine().toCharArray();
        char targetChar = (char) bf.read();
        int count = 0;
        for (char aChar : chars) {
            if (Character.isLetter(aChar) &&
                    (aChar == (char) (targetChar - 32) || aChar == (char) (targetChar + 32))) {
                count++;
            } else {
                count++;
            }
        }
        System.out.println(count);
    }


}
