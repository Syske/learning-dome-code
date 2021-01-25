package io.github.syske.resourceserverdemo.controller;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: springboot-oauth2-demo
 * @description:
 * @author: syske
 * @create: 2020-05-13 09:41
 */
@RestController
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    // 添加一个测试访问接口
    @GetMapping("/user")
    public Authentication getUser(Authentication authentication) {
        log.info("resource: user {}", authentication);
        return authentication;
    }
}
