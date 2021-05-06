package io.github.syske.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ContextLoader;

/**
 * @program: shiro-spring
 * @description:
 * @author: liu yan
 * @create: 2019-11-09 12:37
 */
@Controller
@RequestMapping("/shiro")
public class ShiroController {
    private static final transient Logger log = LoggerFactory.getLogger(ShiroController.class);

    @RequestMapping("/login")
    public String login(@RequestParam(name = "username") String username,
                        @RequestParam(name = "password") String password) {
        // 获取当前用户Subject
        Subject currentUser = SecurityUtils.getSubject();

        // 判断用户是否已经登录
        if (!currentUser.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            token.setRememberMe(true);
            try {
                currentUser.login(token);
            } catch (AuthenticationException ae) {
                log.error("登录失败：" + ae);
                return "redirect:/error.jsp";
            }
        }
        return "redirect:/list.jsp";
    }
}
