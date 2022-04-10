package io.github.syske.function;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @program: function-demo
 * @description: function demo
 * @author: syske
 * @date: 2022-02-15 11:41
 */
public class FunctionDemo {

    public void functionCallback(String name, Function<Exception, String> function) {
        if ("hello".equals(name)) {
            function.apply(new Exception("hello exception"));
        }
    }

    public static void main(String[] args) {
        FunctionDemo functionDemo = new FunctionDemo();
        functionDemo.functionCallback("hello", functionDemo::exceptionHandle);

        functionDemo.functionTest("success", failCallback -> System.out.println("no error"),
                successCallback -> System.out.println("success"));

        functionDemo.functionTest("fail", failCallback -> System.out.println(failCallback.getMessage()),
                System.out::println);
    }

    public String exceptionHandle(Exception exception) {
        System.out.println(exception.getMessage());
        return exception.getMessage();
    }


    public void functionTest(String result, Consumer<Exception> failCallback, Consumer<String> successCallback) {

        if ("fail".equals(result)) {
            failCallback.accept(new Exception("hello exception"));
        }
        if ("success".equals(result)) {
            successCallback.accept(result);
        }
    }
}
