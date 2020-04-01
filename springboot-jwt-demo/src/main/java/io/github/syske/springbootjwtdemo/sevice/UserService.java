package io.github.syske.springbootjwtdemo.sevice;

import io.github.syske.springbootjwtdemo.dao.entity.User;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @program: springboot-jwt-demo
 * @description:
 * @author: syske
 * @create: 2020-03-13 23:52
 */
@Component
public class UserService {

    public User getUser(String username) {
        // 没有此用户直接返回null
        if (! DataSource.getData().containsKey(username))
            return null;

        User user = new User();
        Map<String, String> detail = DataSource.getData().get(username);

        user.setUsername(username);
        user.setPassword(detail.get("password"));
        return user;
    }
}
