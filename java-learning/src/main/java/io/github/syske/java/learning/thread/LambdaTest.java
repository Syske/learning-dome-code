package io.github.syske.java.learning.thread;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author syske
 * @date 2021-04-20 9:44
 */
public class LambdaTest {
    public static void main(String[] args) {
        String[] strings = {"", "234345", "", "341367", "sdfasdf", ""};
        List<String> collect = Arrays.stream(strings).filter(s -> !StringUtils.isEmpty(s)).collect(Collectors.toList());
        collect.forEach(s -> System.out.println(s));
    }
}
