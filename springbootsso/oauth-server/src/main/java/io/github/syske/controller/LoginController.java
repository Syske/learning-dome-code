package io.github.syske.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @program: springbootsso
 * @description:
 * @author: syske
 * @create: 2019-12-13 10:01
 */
@Controller
public class LoginController {
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}
