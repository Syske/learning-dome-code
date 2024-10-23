package io.github.syske;

import io.github.syske.classloader.MyClassLoader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) throws Exception {
        MyClassLoader myClassLoader = new MyClassLoader();
        Class<?> aClass = myClassLoader.findClass("io.github.syske.MyClass");
        System.out.println("classLoader:" + aClass.getClassLoader().getClass());
        MyClass newInstance = (MyClass)aClass.getDeclaredConstructor().newInstance();
        System.out.println("MyInterface classLoader:" + MyInterface.class.getClassLoader().getClass());

        System.out.println(newInstance.getName());
//        invokeForClassLoader(aClass);
    }

    /**
     * 反射用法
     *
     * @param aClass
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
    private static void invokeForClassLoader(Class<?> aClass) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Object newInstance = aClass.getDeclaredConstructor().newInstance();
        System.out.println("obj instance:" + newInstance);
        Method getName = aClass.getMethod("getName");
        Object invoke = getName.invoke(newInstance);
        System.out.println("result:" + invoke);
    }
}