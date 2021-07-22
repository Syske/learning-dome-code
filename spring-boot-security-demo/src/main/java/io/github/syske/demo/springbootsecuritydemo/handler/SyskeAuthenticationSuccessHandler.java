package io.github.syske.demo.springbootsecuritydemo.handler;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * @program: spring-boot-security-demo
 * @description: 登录成功处理器
 * @author: syske
 * @date: 2021-07-22 15:38
 */
@Component
public class SyskeAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        HashMap<String, Object> result = Maps.newHashMap();
        result.put("message", "登录成功");
        result.put("success", Boolean.TRUE);
        result.put("code", 2000);
        httpServletResponse.getWriter().write(JSON.toJSONString(result));
    }
}
