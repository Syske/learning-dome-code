package io.github.syske.springboot.activemq.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @program: springboot-activemq-demo
 * @description: web基本配置
 * @author: syske
 * @date: 2021-05-23 11:11
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/file-index").setViewName("file-index");
    }
}
