package io.github.syske.customannotationdemo.config;

import io.github.syske.customannotationdemo.interceptor.AuthenticationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 鉴权拦截器
        registry.addInterceptor(new AuthenticationInterceptor()).addPathPatterns("/**");
    }
}
