package io.github.sysker.basejavadome;

public class FirstCanRunJavaDemo {
    // 姓名
    String name;

    /**
     * 设置姓名的方法
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 程序运行入口
     * @param args
     */
    public static void main(String[] args) {
        FirstCanRunJavaDemo firstCanRunJavaDemo = new FirstCanRunJavaDemo();
        firstCanRunJavaDemo.setName("第一个可运行java程序");
        System.out.println(firstCanRunJavaDemo.name);
    }
}
