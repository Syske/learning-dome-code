package io.github.syske.springbootjwtdemo.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @program: springboot-jwt-demo
 * @description: jwt令牌
 * @author: syske
 * @create: 2020-03-13 18:30
 */
public class JwtToken implements AuthenticationToken {

    // 密钥
    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}