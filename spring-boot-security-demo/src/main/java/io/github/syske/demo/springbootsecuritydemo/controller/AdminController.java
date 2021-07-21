package io.github.syske.demo.springbootsecuritydemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: spring-boot-security-demo
 * @description: 管理controller
 * @author: syske
 * @date: 2021-07-21 13:58
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("security")
    public Object testSecurity(String name) {
        return "hello, admin -" + name;

    }
}
