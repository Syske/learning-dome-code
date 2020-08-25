package io.github.syske.springbootjwtdemo.config;

import io.github.syske.springbootjwtdemo.filter.ExceptionFilter;
import io.github.syske.springbootjwtdemo.filter.XssFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class WebFilterConfig {

    @Bean
    public FilterRegistrationBean xssFilterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new XssFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/*");
        Map<String, String> initParameters = new HashMap<>();
        initParameters.put("excludes", "/favicon.ico, /images, /css/*,/js/*");
        initParameters.put("isIncludeRichText", "true");
        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean exceptionFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new ExceptionFilter());
        registration.setName("exceptionFilter");
        //此处尽量小，要比其他Filter靠前
        registration.setOrder(-1);
        return registration;
    }
}
