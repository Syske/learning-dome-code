package io.github.syske;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @program: lamubda
 * @description: lamubda表达式
 * @author: syske
 * @create: 2019-12-12 11:25
 */
public class LamubdaTest {
    public static void main(String[] args) {
        LamubdaTest tester = new LamubdaTest();
        // 类型声明
        MathOperation addition = (int a, int b) -> a + b;

        // 不用类型声明
        MathOperation subtraction = (a, b) -> a - b;

        // 大括号中的返回语句
        MathOperation multiplication = (int a, int b) -> {
            return a * b;
        };

        // 没有大括号及返回语句
        MathOperation division = (int a, int b) -> a / b;

        System.out.println("10 + 5 = " + tester.operate(10, 5, addition));
        System.out.println("10 - 5 = " + tester.operate(10, 5, subtraction));
        System.out.println("10 x 5 = " + tester.operate(10, 5, multiplication));
        System.out.println("10 / 5 = " + tester.operate(10, 5, division));

        // 不用括号
        GreetingService greetService1 = message ->
                System.out.println("Hello " + message);

        // 用括号
        GreetingService greetService2 = (message) ->
                System.out.println("Hello " + message);

        greetService1.sayMessage("Runoob");
        greetService2.sayMessage("Google");

        Optional<Integer> result = Stream.of("a", "be", "hello")
                .map(s -> s.length())
                .filter(l -> l <= 3)
                .max((o1, o2) -> o1 - o2);
        System.out.println(result.get());

        Stream<Integer> integerStream = Stream.of("of", "bar", "hello").map(s -> s.length());
        System.out.println(integerStream.filter(l -> l > 3).max((o1, o2) -> o1 - o2));
    }

    interface MathOperation {
        int operation(int a, int b);
    }

    interface GreetingService {
        void sayMessage(String message);
    }

    private int operate(int a, int b, MathOperation mathOperation) {
        return mathOperation.operation(a, b);
    }
}
