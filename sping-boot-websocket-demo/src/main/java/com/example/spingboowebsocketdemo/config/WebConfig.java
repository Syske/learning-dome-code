/* Copyright © 2021 syske. All rights reserved. */
package com.example.spingboowebsocketdemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web配置
 *
 * @author syske
 * @version 1.0
 * @date 2021-07-25 14:03
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/websocket").setViewName("websocket");
        registry.addViewController("/websocket2").setViewName("websocket2");
    }
}
