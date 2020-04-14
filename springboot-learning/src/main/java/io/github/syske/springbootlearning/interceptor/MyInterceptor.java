package io.github.syske.springbootlearning.interceptor;

import io.github.syske.springbootlearning.exception.MyException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //throw new MyException("拦截器错误：MyInterceptor");
        // 这里的异常会完美捕获，并返回
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
       // throw new MyException("拦截器错误：MyInterceptor");
        /** 能捕获异常信息并返回给客户端，但并不会覆盖已经请求成功的返回结果，但会包含在返回结果中，比如我的返回结果：
         {"code":1,"success":true,"msg":"请求成功","result":true}{"code":0,"success":false,"msg":"system error:MyException拦截器错误：MyInterceptor","result":null}
         */
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
       // throw new MyException("拦截器错误：MyInterceptor");
        // 这里抛出的异常并不能被捕获，会直接在后台抛出，相当于回掉函数，请求结果已经返回
    }
}
