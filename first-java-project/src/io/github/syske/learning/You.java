package io.github.syske.learning;

import java.util.ArrayList;

public class You {
    public String name;
    public int age;
    public String gender;

    public You() {
        // 方法操作
        System.out.println("you被创建");
        String[] args = {};
        main(args);
        test("构造方法");
        testSatatic("构造方法");
    }
    public You(String name) {
        // 方法操作
        System.out.println("you被创建，you的名字" + name);
    }
    private You(int age) {
        // 方法操作
        System.out.println("you被创建，you的年龄" + age);
    }
    protected You(String name, int age) {
        // 方法操作
        System.out.println("you被创建，you的名字" + name + "you的年龄" + age);
    }


    public static void testSatatic(String name) {
        System.out.println("我是一个静态方法，我被调用了，name=" + name);
    }

    /**
     * 在实例方法中调用
     */
    public void test(String name) {
        System.out.println("我是一个实例方法，我被调用了，name=" + name);
        testSatatic("实例方法");
    }

    public void test2() {
        System.out.println("我是一个实例方法");
        test("实例方法");
        You you = new You();
    }
    public static void main(String[] args) {
       You you = new You();
       you.test("testName");
    }

    String test() {
        return "test";
    }

    public void eat(String food) {
        System.out.println("吃" + food);
    }

    public void eat(String food, String eatTool) {
        System.out.println("用" + eatTool + "吃" + food);
    }
}
