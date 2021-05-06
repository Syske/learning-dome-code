package io.github.syske.springbootjdbcmybatisdemo.controller;

import io.github.syske.springbootjdbcmybatisdemo.dao.UserDAO;
import io.github.syske.springbootjdbcmybatisdemo.entity.User;
import io.github.syske.springbootjdbcmybatisdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: springboot-jdbc-mybatis-demo
 * @description:
 * @author: syske
 * @create: 2020-08-25 17:30
 */
@RestController
public class UserController {
    @Autowired
    private UserService service;

    @RequestMapping("/test1")
    public List<User> listUsers() {
        return service.listUsers();
    }

}
