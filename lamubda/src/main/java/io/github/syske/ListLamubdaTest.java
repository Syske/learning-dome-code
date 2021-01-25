package io.github.syske;

import java.util.ArrayList;
import java.util.Optional;

/**
 * @program: lamubda
 * @description:
 * @author: syske
 * @create: 2019-12-12 14:29
 */
public class ListLamubdaTest {
    public static void main(String[] args) {
        ArrayList<String> listString = new ArrayList<>();
        listString.add("a");
        listString.add("b");
        listString.add("c");
        listString.add("d");
        Optional<String> first = listString.stream().filter(num -> num != "a").findFirst();
        System.out.println(first.get());
    }
}
