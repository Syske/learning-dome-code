package io.github.syske.springbootjdbcmybatisdemo.service;

import io.github.syske.springbootjdbcmybatisdemo.entity.User;

import java.util.List;

public interface UserService {
    List<User> listUsers();
}
