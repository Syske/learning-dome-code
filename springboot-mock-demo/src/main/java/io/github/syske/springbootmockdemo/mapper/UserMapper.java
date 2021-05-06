package io.github.syske.springbootmockdemo.mapper;

import org.springframework.stereotype.Component;

/**
 * user
 *
 * @author syske
 * @version 1.0
 * @date 2021-04-28 下午10:01
 */
@Component
public class UserMapper {

    public int intsertUser(String userId) {
        System.out.println("保存用户：" + userId);
        return 1;
    }

    public String selectUser(String userId) {
        System.out.println("查询用户：" + userId);
        return userId;
    }
}
