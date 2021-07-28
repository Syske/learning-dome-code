package io.github.syske.demo.springbootsecuritydemo.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @program: spring-boot-security-demo
 * @description:
 * @author: syske
 * @date: 2021-07-22 14:47
 */
public class SyskeAuthenticationException extends AuthenticationException {
    public SyskeAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }

    public SyskeAuthenticationException(String msg) {
        super(msg);
    }
}
