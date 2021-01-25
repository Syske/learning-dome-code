package io.github.syske.springbootjwtdemo.controller;

import io.github.syske.springbootjwtdemo.dao.entity.ReturnEntity;
import io.github.syske.springbootjwtdemo.dao.entity.User;
import io.github.syske.springbootjwtdemo.sevice.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: springboot-jwt-demo
 * @description: jwt控制层
 * @author: syske
 * @create: 2020-03-14 22:13
 */
@RestController
public class JwtController {
    private Logger logger = LoggerFactory.getLogger(JwtController.class);

    @Autowired
    private JwtService service;

    @Autowired
    public JwtController(JwtService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ReturnEntity login(@RequestBody User user) {
        String token = service.login(user);
        return ReturnEntity.successResult(1, token);
    }

    @PostMapping("/checkJwt")
    public ReturnEntity checkJwt(String token) {
        return service.checkJwt(token);
    }

    @PostMapping("/refreshJwt")
    public ReturnEntity refreshJwt(String token) {
            return ReturnEntity.successResult(1, service.refreshJwt(token));
    }

    @PostMapping("/inValid")
    public ReturnEntity inValid(String token) {
        service.inValid(token);
        return ReturnEntity.successResult(1,null);
    }
}
