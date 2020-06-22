package io.github.syske.oauth2.demo.config.service;


import io.github.syske.oauth2.demo.entity.Role;
import io.github.syske.oauth2.demo.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;

/**
 * @program: springboot-oauth2-demo
 * @description:
 * @author: syske
 * @create: 2020-05-13 16:23
 */
public class MyUserDetailsService implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("用户的用户名: {}", username);
        // TODO 根据用户名，查找到对应的密码，与权限

        // 封装用户信息，并返回。参数分别是：用户名，密码，用户权限
        String encode = new BCryptPasswordEncoder().encode("123");
        //123456   "$2a$10$rE5.RvkHaB06t.9GjGeaW.jNHysRQpBXObl3ZSahzBesfq7tAkX56"
        User user = new User(username, encode,
                Arrays.asList(new Role("admin")));
        user.setEnabled(true);
        return user;
    }
}