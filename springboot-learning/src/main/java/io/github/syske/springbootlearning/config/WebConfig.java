package io.github.syske.springbootlearning.config;

import io.github.syske.springbootlearning.convert.DateConverter;
import io.github.syske.springbootlearning.convert.SyskeConverter;
import io.github.syske.springbootlearning.filter.ExceptionFilter;
import io.github.syske.springbootlearning.filter.MyFilter;
import io.github.syske.springbootlearning.filter.MyFilter2;
import io.github.syske.springbootlearning.interceptor.MyInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyInterceptor());
    }

    @Bean
    public FilterRegistrationBean myFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new MyFilter());
        registration.setName("myFilter");
        registration.addUrlPatterns("/test2", "/test3");
        //此处尽量小，要比其他Filter靠前
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public FilterRegistrationBean myFilter2Registration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new MyFilter2());
        registration.setName("myFilter2");
        registration.addUrlPatterns("/test2", "/test3");
        //此处尽量小，要比其他Filter靠前
        registration.setOrder(2);
        return registration;
    }

    /**
     * 配置拦截器
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean exceptionFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new ExceptionFilter());
        registration.setName("exceptionFilter");
        //此处尽量小，要比其他Filter靠前
        registration.setOrder(-1);
        return registration;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new SyskeConverter());
        registry.addConverter(new DateConverter());
    }
}
