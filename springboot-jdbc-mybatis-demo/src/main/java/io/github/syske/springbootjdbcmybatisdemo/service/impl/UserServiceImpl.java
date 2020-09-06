package io.github.syske.springbootjdbcmybatisdemo.service.impl;

import io.github.syske.springbootjdbcmybatisdemo.dao.UserDAO;
import io.github.syske.springbootjdbcmybatisdemo.entity.User;
import io.github.syske.springbootjdbcmybatisdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: springboot-jdbc-mybatis-demo
 * @description:
 * @author: syske
 * @create: 2020-08-25 17:58
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO dao;

    @Override
    public List<User> listUsers() {
        return dao.listUsers();
    }
}
