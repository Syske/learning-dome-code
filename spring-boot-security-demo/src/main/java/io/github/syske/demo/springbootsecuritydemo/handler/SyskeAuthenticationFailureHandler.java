package io.github.syske.demo.springbootsecuritydemo.handler;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * @program: spring-boot-security-demo
 * @description: 登录失败处理器
 * @author: syske
 * @date: 2021-07-22 14:00
 */
@Component
public class SyskeAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        HashMap<String, Object> result = Maps.newHashMap();
        result.put("message", e.getMessage());
        result.put("success", Boolean.FALSE);
        result.put("code", 1000);
        httpServletResponse.getWriter().write(JSON.toJSONString(result));
    }
}
