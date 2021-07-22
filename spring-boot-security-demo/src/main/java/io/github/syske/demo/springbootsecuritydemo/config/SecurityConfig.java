/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.demo.springbootsecuritydemo.config;

import io.github.syske.demo.springbootsecuritydemo.service.ReaderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import io.github.syske.demo.springbootsecuritydemo.handler.SyskeAuthenticationFailureHandler;
import io.github.syske.demo.springbootsecuritydemo.handler.SyskeAuthenticationProvider;
import io.github.syske.demo.springbootsecuritydemo.handler.SyskeAuthenticationSuccessHandler;

/**
 * 配置类
 *
 * @author syske
 * @version 1.0
 * @date 2021-07-20 7:50
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    private SyskeAuthenticationFailureHandler handler;

    @Autowired
    private SyskeAuthenticationProvider syskeAuthenticationProvider;

    @Autowired
    private SyskeAuthenticationSuccessHandler successHandler;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(syskeAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            // 限定 ” /user/welcome ”请求赋予角色 ROLE_USER 或者 ROLE_ADMIN
                .antMatchers("/welcome").hasAnyRole("USER", "ADMIN", "TEST")
            // 限定 ” /admin/ ”下所有请求权限赋予角色 ROLE_ADMIN
                .antMatchers("/admin/**").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers("/test/**").hasAnyAuthority("ROLE_TEST", "ROLE_ADMIN")
                .antMatchers("/user/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
            // 其他路径允许签名后访问
//            .anyRequest().permitAll()
            // 对于没有配置权限的其他请求允许匿名访问
            .and().anonymous()
            // 使用spring security 默认的登录页面
            .and().formLogin().loginPage("/userLogin").loginProcessingUrl("/loginService")
             .failureForwardUrl("/fail")
             .successForwardUrl("/user/welcome")
            .failureHandler(handler)
                .defaultSuccessUrl("/welcome")
                .successHandler(successHandler)
                .and().rememberMe()
            .tokenValiditySeconds(30).key("remember-me")
            // 启动 HTTP 基础验证
            .and().httpBasic();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
