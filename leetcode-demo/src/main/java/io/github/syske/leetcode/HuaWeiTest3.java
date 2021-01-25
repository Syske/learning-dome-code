package io.github.syske.leetcode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @program: leetcode-demo
 * @description:
 * @author: syske
 * @create: 2020-11-04 16:41
 */
public class HuaWeiTest3 {
    public static void main(String[] args) throws Exception{
        //Scanner s = new Scanner(System.in);
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String input = null;
        int i = 0;
        input = bf.readLine();
        int number = Integer.parseInt(bf.readLine());
        int s = (int) Math.floor(number/2);
        String[] strings = new String[Integer.parseInt(input)];
        while((input=bf.readLine())!=null){


        //while (s.hasNext()) {
            strings[i] = input;
            i++;
        }
        Arrays.sort(strings);

        for (String string : strings) {
            System.out.println(string);
        }
    }
}
