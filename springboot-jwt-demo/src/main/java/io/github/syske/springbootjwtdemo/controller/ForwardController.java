package io.github.syske.springbootjwtdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: springboot-jwt-demo
 * @description: 登陆成功后进行请求重定向
 * @author: syske
 * @create: 2020-03-14 23:32
 */
@Controller
public class ForwardController {
    @GetMapping("/forwardTo")
    public String forwardTo(String url) {
        return "redirect:" + url;

    }
}
