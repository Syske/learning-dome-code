package io.github.syske.demo.springbootsecuritydemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: spring-boot-security-demo
 * @description: 普通用户controller
 * @author: syske
 * @date: 2021-07-21 13:59
 */
@RestController
@RequestMapping("/user")
public class UserController {

        @GetMapping("security")
        public Object testSecurity(String name) {
            return "hello, user -" + name;

        }

    @GetMapping("welcome")
    public Object welcome(String name) {
        return "welcome, user -" + name;

    }
}
