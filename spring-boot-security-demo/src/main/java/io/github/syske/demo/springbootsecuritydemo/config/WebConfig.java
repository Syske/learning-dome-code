/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.demo.springbootsecuritydemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web基础配置
 *
 * @author syske
 * @version 1.0
 * @date 2021-07-22 8:24
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/userLogin").setViewName("login");
        registry.addViewController("/welcome").setViewName("welcome");
        registry.addViewController("/user/hello").setViewName("user/hello");
        registry.addViewController("/admin/hello").setViewName("admin/hello");
        registry.addViewController("/test/hello").setViewName("test/hello");
        registry.addViewController("/index").setViewName("index");
    }
}
