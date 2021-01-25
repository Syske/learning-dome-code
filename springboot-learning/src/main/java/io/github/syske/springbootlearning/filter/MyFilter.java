package io.github.syske.springbootlearning.filter;

import io.github.syske.springbootlearning.exception.MyException;

import javax.servlet.*;
import java.io.IOException;

public class MyFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        throw new MyException("MyFilter过滤器抛出异常");
        //filterChain.doFilter(servletRequest, servletResponse);
    }

}
