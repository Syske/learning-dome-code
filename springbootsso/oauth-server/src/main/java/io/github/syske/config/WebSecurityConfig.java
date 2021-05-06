package io.github.syske.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @program: springbootsso
 * @description:
 * @author: syske
 * @create: 2019-12-13 09:57
 */
@Configuration
@Order(1)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requestMatchers()
                .antMatchers("/login")
                .antMatchers("/oauth/authorize")
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .and().csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       /* //使用内存模拟数据库查询的用户
        auth.inMemoryAuthentication() //内存认证
                .withUser("admin")//admin 内存认证用户名
                .password(passwordEncoder().encode("123456"))//被加密的123456密码
                .roles("admin").authorities("admin");;//ROLE_ADMIN的角色*/
        //        在内存中定义用户，方便测试使用
        auth
                .inMemoryAuthentication()
                .withUser("cj")
                .password(passwordEncoder().encode("123"))
                .roles("USER")
                .and()
                .withUser("admin")
                .password(passwordEncoder().encode("123"))
                .authorities("admin");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
