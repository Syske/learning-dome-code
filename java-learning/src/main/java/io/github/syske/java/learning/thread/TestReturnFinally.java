package io.github.syske.java.learning.thread;

/**
 * @program: java-learning
 * @description: 测试return和finally
 * @author: syske
 * @create: 2021-03-18 21:34
 */
public class TestReturnFinally {

    private static String getStr() throws Exception {
        try {
            return "test";
        } catch (Exception e) {
            throw new Exception("未知错误");
        } finally {
            System.out.println("finally被执行");
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println(getStr());
    }
}
