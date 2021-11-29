package io.github.syske;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @program: lambda-demo
 * @description:
 * @author: syske
 * @date: 2021-11-26 13:15
 */
public class LambdaTest {
    public static void main(String[] args) {
        User user1 = new User(1L, "syske");
        User user2 = new User(2L, "yun zhong zhi");
        User user3 = new User(2L, "yun zhong zhi 2");
        List<User> userList = Lists.newArrayList(user1, user2, user3);
        Map<Long, List<User>> collect8 = userList.stream().collect(Collectors.groupingBy(User::getId));
        System.out.println(collect8);
        Map<Long, Long> collect9 = userList.stream().collect(Collectors.groupingBy(User::getId, Collectors.counting()));
        System.out.println(collect9);
        Map<Long, Long> collect10 = userList.stream().collect(Collectors.groupingBy(User::getId, Collectors.summingLong(User::getId)));
        System.out.println(collect10);
        Map<Long, Double> collect11 = userList.stream().collect(Collectors.groupingBy(User::getId, Collectors.averagingLong(User::getId)));
        System.out.println(collect11);
        Double collect12 = userList.stream().collect(Collectors.averagingLong(User::getId));
        Long collect13 = userList.parallelStream().collect(Collectors.reducing(User::getId));

        Map<Long, String> collect6 = userList.stream().collect(Collectors.toMap(User::getId, User::getUsername, (a, b) -> a));
        Map<Long, User> collect7 = userList.stream().collect(Collectors.toMap(User::getId, Function.identity()));


        List<User> users = userList.stream().filter(user -> user.getUsername().contains("s")).collect(Collectors.toList());
        System.out.println(users);
        // 用户
        System.out.printf("userList = %s\n",userList);
        // 用户名list
        List<String> userNameList = userList.stream().map(User::getUsername).collect(Collectors.toList());
        System.out.printf("userNameList = %s\n",userNameList);

        List<String> userStrList = userList.stream().map(String::valueOf).collect(Collectors.toList());

        List<String> collect = userList.stream().map(JSON::toJSONString).collect(Collectors.toList());
        System.out.println(collect);
        System.out.println(userStrList);

        ArrayList<String> strings = Lists.newArrayList("0912", "1930", "1977", "1912");
        String collect3 = strings.stream().collect(Collectors.joining());
        List<Integer> integers = strings.stream().map(Integer::parseInt).collect(Collectors.toList());
        System.out.println(integers);

        ArrayList<Integer> ages = Lists.newArrayList(89, 97, 99, 12, 15, 45, 55, 35, 25, 18);
        List<Integer> collect1 = ages.stream().filter(a -> a > 40).sorted(Comparator.naturalOrder()).collect(Collectors.toList());
        Optional<Integer> max = ages.stream().max(Comparator.naturalOrder());
        System.out.println(max.get());
        String collect2 = ages.stream().map(String::valueOf).collect(Collectors.joining(","));
        System.out.println(collect2);
        System.out.println(collect1);

        boolean anyMatch = userList.stream().anyMatch(user -> user.getUsername().contains("s"));
        System.out.println(anyMatch);

        ArrayList<String> strings2 = Lists.newArrayList("ab", "ba", "ca", "dd", "Zf", "ZF", "zF", "zf", "cl");

        strings2.sort(Comparator.naturalOrder());
        String s = strings2.stream().max(Comparator.naturalOrder()).get();
        System.out.println(s);
        System.out.println("sorted strings2" + strings2);

        String s2 = strings.stream().max(Comparator.reverseOrder()).get();
        System.out.println(s2);

        ArrayList<String> strings3 = Lists.newArrayList("09,12", "19,30", "19,77", "19,12");
        List<String[]> collect4 = strings3.stream().map(string -> string.split(",")).collect(Collectors.toList());
        List<String> collect5 = strings3.stream().flatMap(string -> Arrays.stream(string.split(","))).collect(Collectors.toList());
        System.out.println(collect4);
        System.out.println(collect5);


    }
}
