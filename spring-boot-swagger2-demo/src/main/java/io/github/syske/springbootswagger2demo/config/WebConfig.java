package io.github.syske.springbootswagger2demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * @program: spring-boot-swagger2-demo
 * @description:
 * @author: syske
 * @create: 2019-12-02 15:25
 */


@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     *  如果配置跨域，就增加这个配置
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("static/**").addResourceLocations("/static/");
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
    }


    /**
     * 跨域支持配置
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowCredentials(true).allowedOrigins("*").allowedMethods("GET", "PUT", "DELETE", "POST", "OPTIONS").maxAge(3600);
    }
}