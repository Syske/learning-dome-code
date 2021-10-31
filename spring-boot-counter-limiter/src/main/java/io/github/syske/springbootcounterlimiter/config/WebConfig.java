/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.springbootcounterlimiter.config;

import io.github.syske.springbootcounterlimiter.interceptor.CounterLimiterHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author syske
 * @version 1.0
 * @date 2021-10-31 21:59
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private CounterLimiterHandlerInterceptor counterLimiterHandlerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 计数器限速
        registry.addInterceptor(counterLimiterHandlerInterceptor).addPathPatterns("/**");
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
