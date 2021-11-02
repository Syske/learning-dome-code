/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.springbootbucketlimit.config;

import io.github.syske.springbootbucketlimit.interceptor.LeakyBucketLimiterInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author syske
 * @version 1.0
 * @date 2021-11-01 22:36
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LeakyBucketLimiterInterceptor limiterInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(limiterInterceptor).addPathPatterns("/**");
    }
}
