package io.github.syske.leetcode;

import java.util.ArrayList;

/**
 * @program: leetcode-demo
 * @description:
 * @author: syske
 * @create: 2020-11-30 20:11
 */
public class HuaWeiTest7 {
    public static void main(String[] args) {
        String input = "1234*123/2-2+2+3.4";
        /**
         * 12/0+3*5
         *
         */
        char[] chars = input.toCharArray();
        StringBuilder numberBuilder = new StringBuilder();

        boolean[] isJia = new boolean[input.length()];
        boolean[] isJian = new boolean[input.length()];
        boolean[] isCheng = new boolean[input.length()];
        boolean[] isChu = new boolean[input.length()];
        int count = 0;
        ArrayList<Integer> loc = new ArrayList<>();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c == '*') {
                numberBuilder.append(",").append(c).append(",");
                count++;
                isCheng[i] = true;
                loc.add(i);
            } else if (c == '/') {
                numberBuilder.append(",").append(c).append(",");
                count++;
                isChu[i] = true;
                loc.add(i);
            } else if (c == '+') {
                count++;
                numberBuilder.append(",").append(c).append(",");
                isJia[i] = true;
                loc.add(i);
            } else if (c == '-') {
                count++;
                numberBuilder.append(",").append(c).append(",");
                isJian[i] = true;
            } else if (Character.isDigit(c)) {
                numberBuilder.append(c);
            } else if (c == '.') {
                numberBuilder.append(c);
            }


        }
        String[] split = numberBuilder.toString().split(",");
        double sum = 0.0;
        double temp = 0.0;
        try {

            for (int i = 0; i < split.length; i++) {
                double temp1 = temp;
                String s = split[i];
                if (s.equals("*")) {
                    sum = sum * temp1;
                } else if (s.equals("/")) {
                    sum = sum / temp1;
                } else if (s.equals("+")) {
                    sum = sum + temp1;
                } else if (s.equals("-")) {
                    sum = sum - temp1;
                } else {
                    temp = Double.parseDouble(s);
                }
                if (i == 0) {
                    sum = temp;
                }

            }
            System.out.println(sum);
        } catch (NumberFormatException e) {
            System.out.println("L");
        }


    }
}
