/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.demo.springbootsecuritydemo.handler;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * LogoutSuccessHandler
 *
 * @author syske
 * @version 1.0
 * @date 2021-07-24 8:18
 */
@Component
public class SyskeLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        // 登出成功后的相关操作
        System.out.println("退出登录成功");
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        HashMap<String, Object> result = Maps.newHashMap();
        result.put("message", "退出登录成功");
        result.put("success", Boolean.TRUE);
        result.put("code", 2000);
        httpServletResponse.getWriter().write(JSON.toJSONString(result));
    }
}
