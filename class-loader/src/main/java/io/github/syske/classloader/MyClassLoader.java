package io.github.syske.classloader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MyClassLoader extends ClassLoader {
    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classData = loadClassData(name);
        if (classData == null) {
            throw new ClassNotFoundException();
        }
        System.out.println("load class data end");
        return defineClass(name, classData, 0, classData.length);
    }

    protected static byte[] loadClassData(String className) {
        String fileName = "D:/workspace/learning/learning-dome-code/class-loader/target/classes/" + className.replace('.', '/') + ".class";
        try (InputStream is = Files.newInputStream(Paths.get(fileName));
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            int ch;
            while (-1 != (ch = is.read())) {
                bos.write(ch);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
