/**
 * @program: leetcode-demo
 * @description:
 * @author: syske
 * @create: 2020-11-04 16:29
 */

public class Test {
    public static void main(String[] args) {
        String input = "I";
        String[] strings = input.split(" ");
        for (int i = strings.length-1; i >= 0 ; i--) {
            System.out.println(strings[i]);
        }
    }
}
