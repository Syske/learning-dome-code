package io.github.syske.springbootjdbcmybatisdemo.dao;

import io.github.syske.springbootjdbcmybatisdemo.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDAO {
    List<User> listUsers();
}
