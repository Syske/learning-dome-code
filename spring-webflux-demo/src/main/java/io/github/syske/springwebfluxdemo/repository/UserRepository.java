package io.github.syske.springwebfluxdemo.repository;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;

import io.github.syske.springwebfluxdemo.repository.entity.User;
import reactor.core.publisher.Mono;

/**
 * @program: spring-webflux-demo
 * @description: 用户存储
 * @author: syske
 * @date: 2021-07-30 13:02
 */
@Service
public class UserRepository {
    Map<Long, User> userMap = Maps.newHashMap();
    {
        userMap.put(123456L, new User());
    }
    public Mono<User> findById(Long id) {
        return Mono.just(userMap.get(id));
    }
}
