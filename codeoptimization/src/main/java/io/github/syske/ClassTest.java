package io.github.syske;

import java.io.File;
import java.lang.reflect.Field;
import java.text.Annotation;
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
        part2();
    }

    private static void part1() {
        System.out.println("*********************Object************************");
        Object object = new Object();
        printClassInfo(object.getClass());

        System.out.println("*********************字符串************************");
        String testStrin = "test";
        printClassInfo(testStrin.getClass());

        System.out.println("*********************包装类************************");
        Integer integer = new Integer(0);
        printClassInfo(integer.getClass());

        Byte b = 'c';
        printClassInfo(b.getClass());

        Double d = new Double(12);
        printClassInfo(d.getClass());

        Float f = new Float(123);
        printClassInfo(f.getClass());

        Long l = new Long(10);
        printClassInfo(l.getClass());


        System.out.println("*********************基本类型************************");
        printClassInfo(int.class);
        printClassInfo(long.class);
        printClassInfo(double.class);
        printClassInfo(void.class);

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

        System.out.println("*********************枚举类型************************");
        printClassInfo(Enum.class);
        printClassInfo(MyEnum.class);

        System.out.println("*********************注解类型************************");
        printClassInfo(Annotation.class);
        printClassInfo(MyAnnotation.class);

        System.out.println("*********************接口类型************************");
        printClassInfo(MyInterface.class);

        System.out.println("*********************Void************************");
        printClassInfo(Void.class);

        byte a = 'c';
        System.out.println(a);
    }

    private static void part2() {
        try {
            Class<?> aClass = Class.forName("io.github.syske.ForNameTestObject");
            // 获取公有字段属性（public修饰）
            Field[] fields = aClass.getFields();
            // 获取所有字段属性
            Field[] declaredFields = aClass.getDeclaredFields();
            for (Field field : declaredFields) {
                System.out.println("field: " + field);
                System.out.println("field.getName: " + field.getName());
            }
            Object o = aClass.newInstance();
            ForNameTestObject o1 = (ForNameTestObject) o;
            System.out.println(o1.getName());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    private static void printClassInfo(Class clazz) {
        System.out.println("ClassName:" + clazz.getName());
//        System.out.println("ClassSimpleName:" + clazz.getSimpleName());
//        System.out.println("GenericSuperclass:" + clazz.getGenericSuperclass());
//        System.out.println("SuperClassName:" + clazz.getSuperclass());
        //       System.out.println("toString:" + clazz.toString());
        //      System.out.println("toGenericString:" + clazz.toGenericString());
        System.out.println("--------------------------------------------");
    }
}
