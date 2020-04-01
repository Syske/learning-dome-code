package io.github.syske;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: codeoptimization
 * @description: 类型测试
 * @author: syske
 * @create: 2020-02-20 14:09
 */
public class ClassTest {
    public static void main(String[] args) {
        System.out.println("*********************Object************************");
        Object object = new Object();
        printClassInfo(object.getClass());

        System.out.println("*********************字符串************************");
        String testStrin = "test";
        printClassInfo(testStrin.getClass());
        Integer integer = new Integer(0);
        printClassInfo(integer.getClass());

        System.out.println("*********************包装类************************");
        Byte b = 'c';
        printClassInfo(b.getClass());

        Double d = new Double(12);
        printClassInfo(d.getClass());

        Float f = new Float(123);
        printClassInfo(f.getClass());

        Long l = new Long(10);
        printClassInfo(l.getClass());

        System.out.println("*********************数组************************");

        Object[] array = new Object[0];
        printClassInfo(array.getClass());

        Object[][] array2 = new Object[0][];
        printClassInfo(array2.getClass());

        Object[][][] array3 = new Object[0][][];
        printClassInfo(array3.getClass());

        Object[][][][] array4 = new Object[0][][][];
        printClassInfo(array4.getClass());

        System.out.println("*********************集合************************");
        List<Object> list = new ArrayList<>();
        printClassInfo(list.getClass());
        printClassInfo(List.class);

        byte a = 'c';
        System.out.println(a);
    }

    private static void printClassInfo(Class clazz) {
        System.out.println("ClassName:" + clazz.getName());
        System.out.println("ClassSimpleName:" + clazz.getSimpleName());
        System.out.println("GenericSuperclass:" + clazz.getGenericSuperclass());
        System.out.println("SuperClassName:" + clazz.getSuperclass());
        System.out.println("--------------------------------------------");
    }
}
