package io.github.syske.demo.springbootsecuritydemo.controller;

import com.google.common.collect.Maps;
import io.github.syske.demo.springbootsecuritydemo.service.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @program: spring-boot-security-demo
 * @description:
 * @author: syske
 * @date: 2021-07-22 13:29
 */
@RestController
public class LoginController {

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

    @GetMapping("/loginService")
    public Object login(String username, String password) {
        HashMap<String, Object> result = Maps.newHashMap();
        UserDetails userDetails = loadUserByUsername(username);
        if (Objects.isNull(userDetails)) {
            result.put("code", 0001);
            result.put("message", "用户不存在");
            return result;
        }
        if (userDetails.getPassword().equals(new BCryptPasswordEncoder().encode(password))) {
            result.put("code", 2000);
            result.put("message", "登录成功");
            return result;
        } else {
            result.put("code", 0002);
            result.put("message", "用户名或密码错误");
            return result;
        }

    }
}
