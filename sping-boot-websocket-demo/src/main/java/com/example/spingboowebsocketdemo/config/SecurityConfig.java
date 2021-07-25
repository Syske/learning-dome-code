/* Copyright © 2021 syske. All rights reserved. */
package com.example.spingboowebsocketdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 安全配置
 *
 * @author syske
 * @version 1.0
 * @date 2021-07-25 15:21
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("zhongkui").password(passwordEncoder.encode("123456")).roles("user")
                .and().withUser("fuxi").password(passwordEncoder.encode("123456")).roles("user")
                .and().withUser("pangu").password(passwordEncoder.encode("123456")).roles("user")
                .and().withUser("nezha").password(passwordEncoder.encode("123456")).roles("user")
                .and().withUser("nvwa").password(passwordEncoder.encode("123456")).roles("user")
                .and().withUser("jiangziya").password(passwordEncoder.encode("123456")).roles("user")
        .and().passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin().and()
                .httpBasic()
                .and().logout().logoutUrl("/logout");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
