package io.github.syske.demo.springbootsecuritydemo.controller;

import com.google.common.collect.Maps;
import io.github.syske.demo.springbootsecuritydemo.service.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Objects;

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
}
