package io.github.syske.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

/**
 * @program: springbootsso
 * @description:
 * @author: syske
 * @create: 2019-12-13 11:42
 */
@Controller
public class InfoController {
    @GetMapping("/getUser")
    public ResponseEntity<Object> userPage(Principal principal) {
        //客户端认证成功后返回这个用户信息
        return new ResponseEntity<Object>(principal, HttpStatus.OK);
    }

    @GetMapping("/success")
    public String loginSuccess() {
        return "loginSuccess";
    }

    @GetMapping("/")
    public String indexPage() {
        return "index";
    }
}
