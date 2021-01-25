package io.github.syske.customannotationdemo.interceptor;

import io.github.syske.customannotationdemo.annotation.CheckAuth;
import io.github.syske.customannotationdemo.exception.AuthException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 鉴权拦截器
 */
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            if (handlerMethod.hasMethodAnnotation(CheckAuth.class)) {
                System.out.println("有CheckAuth注解");
                String token = request.getParameter("token");
                if (!"ABCDEF12345".equals(token)) {
                    throw new AuthException();
                }
            }
        }
        return true;
    }
}
