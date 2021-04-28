package io.github.syske.leetcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @program: leetcode-demo
 * @description:
 * @author: syske
 * @create: 2020-11-04 11:37
 */
public class HuaWeiTest {

    public static int rounding(float source) {
        return Math.round(source);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String input = null;
        while ((input = bf.readLine()) != null) {
            int a = Math.round(Float.parseFloat(input));
            /*int a = Integer.parseInt(input.substring(0,input.indexOf(".")));
            int b = Integer.parseInt(input.substring(input.indexOf(".")+1,input.indexOf(".")+2));
            if(b>=5){
                a+=1;
            }*/
            System.out.println(a);
        }
    }


}
