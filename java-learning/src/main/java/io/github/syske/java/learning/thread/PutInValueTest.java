package io.github.syske.java.learning.thread;

import javax.sound.midi.Soundbank;

public class PutInValueTest {
    public static void main(String[] args) {
//        String s = "234324";
        String s = new String("234324");
//        int s = 1232;
        printlnStr(s);
        System.out.println(s);
    /*    Person p = new Person();
        p.name = "张三";
        changePerson(p);
        System.out.println(p.name);
        */
    }

    private static void printlnStr(String str) {
        System.out.println(str);
        str = "12323213";
        System.out.println(str);
    }

    private static void printlnStr(int i) {
        System.out.println(i);
        i = 12323213;
        System.out.println(i);
    }

    private static void changePerson(Person person) {
        System.out.println(person.name);
        //person = new Person();
        person.name = "里斯";
        System.out.println(person.name);
    }
}

class Person {
    String name;

}
