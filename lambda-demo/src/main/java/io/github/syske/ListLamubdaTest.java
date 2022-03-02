package io.github.syske;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @program: lamubda
 * @description:
 * @author: syske
 * @create: 2019-12-12 14:29
 */
public class ListLamubdaTest {
    public static void main(String[] args) {
        /*ArrayList<String> listString = new ArrayList<>();
        listString.add("a");
        listString.add("b");
        listString.add("c");
        listString.add("d");
        Optional<String> first = listString.stream().filter(num -> !Objects.equals(num, "a")).findFirst();
        System.out.println(first.get());*/


        String result = "failed";
        new ListLamubdaTest().callBack(result, s -> {System.out.println("业务执行成功" + s);
        }, f -> {System.out.println("业务执行失败" + f);
        });

        new ListLamubdaTest().functionTest("syske", "hello", parameter -> System.out.println( "syske, " + parameter));
        new ListLamubdaTest().testFunction(1, 2, ListLamubdaTest::add);
        new ListLamubdaTest().setMyFunctionInterface2("parameter1", "parameter2", "parameter3", "parameter4", "parameter5",
                (t, r, u, x, y) -> System.out.printf("%s.%s.%s.%s.%s", t, r, u, x,y));

    }

    public void syskeFun(String parameter) {
        System.out.println( "syske, " + parameter);
    }

    public void dealThing(String parameter) {
        new ListLamubdaTest().functionTest("syske", "hello", this::syskeFun);
        new ListLamubdaTest().functionTest("syske", "hello", this::syskeFun);
        // 执行业务
        String result = "123".equals(parameter) ? "success" : "failed";
        if ("success".equals(result)) {
            System.out.println("执行成功");
        }

        if ("failed".equals(result)) {
            System.out.println("执行失败");
        }
    }

    /**
     * 结果回调
     * @param result
     * @param successFun
     * @param failedFun
     */
    public void callBack(String result, Function<String, String> successFun, Function<String, String> failedFun) {
        if ("success".equals(result)) {
            successFun.apply(result);
        }

        if ("failed".equals(result)) {
            failedFun.apply(result);
        }
    }


    /**
     * 结果回调
     * @param result
     * @param successFun
     * @param failedFun
     */
    public void callBack(String result, Consumer<String> successFun, Consumer<String> failedFun) {
        if ("success".equals(result)) {
            successFun.accept(result);
        }

        if ("failed".equals(result)) {
            failedFun.accept(result);
        }
    }

    public void functionTest(String test, String parameter, MyFunctionInterface<String> myFunctionInterface) {
        if ("syske".equals(test)) {
            myFunctionInterface.syske(parameter);
        }
    }

    private static Integer add(int a, int b) {
        return a + b;
    }

    public void testFunction(Integer a, Integer b, BiFunction<Integer, Integer, Integer> function) {
        System.out.println(function.apply(a, b));

    }



    public void setMyFunctionInterface2(String t, String r, String u, String x, String y, MyFunctionInterface2<String, String, String, String, String> interface2) {
        interface2.syske(t, r, u, x, y);
    }


}
