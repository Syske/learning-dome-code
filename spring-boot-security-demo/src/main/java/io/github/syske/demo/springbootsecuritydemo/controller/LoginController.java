package io.github.syske.demo.springbootsecuritydemo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;

import io.github.syske.demo.springbootsecuritydemo.service.ReaderRepository;

/**
 * @program: spring-boot-security-demo
 * @description:
 * @author: syske
 * @date: 2021-07-22 13:29
 */
@RestController
public class LoginController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private static Map<String, UserDetails> userDetailsMap = Maps.newHashMap();

    static {
        userDetailsMap.put("admin", new ReaderRepository.UserInfo("admin", encrypt("admin"), "ROLE_ADMIN"));
        userDetailsMap.put("user", new ReaderRepository.UserInfo("user", encrypt("123456"), "ROLE_USER"));
        userDetailsMap.put("test", new ReaderRepository.UserInfo("test", encrypt("test"), "ROLE_TEST"));
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (userDetailsMap.containsKey(username)) {
            return userDetailsMap.get(username);
        } else {
            throw new UsernameNotFoundException("用户不存在");
        }
    }

    private static String encrypt(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

}
