package io.github.syske;

import com.google.common.collect.Lists;

import java.util.List;
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
        List<User> userList = Lists.newArrayList(user1, user2);
        // 用户
        System.out.printf("userList = %s\n",userList);
        // 用户名list
        List<String> userNameList = userList.stream().map(User::getUsername).collect(Collectors.toList());
        System.out.printf("userNameList = %s\n",userNameList);

        List<String> userStrList = userList.stream().map(String::valueOf).collect(Collectors.toList());

        System.out.println(userStrList);
    }
}
