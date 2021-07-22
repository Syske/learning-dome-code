package io.github.syske.demo.springbootsecuritydemo.handler;

import com.google.common.collect.Maps;
import io.github.syske.demo.springbootsecuritydemo.exception.SyskeAuthenticationException;
import io.github.syske.demo.springbootsecuritydemo.service.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Objects;

/**
 * @program: spring-boot-security-demo
 * @description: 登录认证处理器
 * @author: syske
 * @date: 2021-07-22 14:12
 */
@Configuration("syskeAuthenticationProvider")
public class SyskeAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private ReaderRepository readerRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDetails userDetails = readerRepository.loadUserByUsername(authentication.getName());
        if (Objects.isNull(userDetails)) {
            authentication.setAuthenticated(false);
           throw new SyskeAuthenticationException("用户不存在");
        }
        String credentials = (String)authentication.getCredentials();
        String encodePassword = passwordEncoder.encode(credentials);
        if (passwordEncoder.matches(credentials, encodePassword)) {
            return new UsernamePasswordAuthenticationToken(userDetails, encodePassword, userDetails.getAuthorities());
        } else {
            authentication.setAuthenticated(false);
            throw new SyskeAuthenticationException("用户名或密码错误");
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
