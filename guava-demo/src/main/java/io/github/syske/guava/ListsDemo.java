package io.github.syske.guava;

import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @program: guava-demo
 * @description: guava容器相关示例
 * @author: syske
 * @date: 2021-05-01 14:16
 */
public class ListsDemo {

    public static void main(String[] args) {
        List<String> stringList1 = new ArrayList<>();
        stringList1.add("hello");
        stringList1.add("world");

        List<String> stringList2 = Lists.newArrayList("hello", "world", "");
        List<Boolean> transform = Lists.transform(stringList2, s -> !"".equals(s));

        List<Boolean> transform2 = Lists.transform(stringList2, new Function<String, Boolean>() {
            @Override
            public @Nullable Boolean apply(@Nullable String input) {
                return !"".equals(input);
            }
        });
        String[] strings = new String[] {"1231", "23432423"};
        ArrayList<String> strings1 = Lists.newArrayList(strings);

        LinkedList<Object> objects = Lists.newLinkedList();

        List<List<String>> partition = Lists.partition(stringList1, 200);

        List<Integer> integerList = Lists.newArrayList(1, 2, 3, 4);
        List<Integer> reverse = Lists.reverse(integerList);
        System.out.println(reverse);
        List<Boolean> transform3 = Lists.transform(stringList2, s -> !"".equals(s));
        System.out.println(transform);

    }

    public static boolean isNotNullAndEmpty(String str) {
        return str != null && !str.isEmpty();
    }
}
