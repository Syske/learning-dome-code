package io.github.syske.springbootjwtdemo.filter;

import io.github.syske.springbootjwtdemo.exception.AuthorizationException;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component
public class ExceptionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            Throwable eCause = e.getCause();
            // 异常捕获，发送到error controller
            request.setAttribute("filter.error", eCause);
            //将异常分发到/error/exthrow控制器
            request.getRequestDispatcher("/error/exthrow").forward(request, response);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {

    }
}
