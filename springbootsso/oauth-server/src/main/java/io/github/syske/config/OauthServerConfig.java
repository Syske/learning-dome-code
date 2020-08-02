package io.github.syske.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

/**
 * @program: springbootsso
 * @description:
 * @author: syske
 * @create: 2019-12-13 09:59
 */
@Configuration
@EnableAuthorizationServer
public class OauthServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void configure(final AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(final ClientDetailsServiceConfigurer clients)
            throws Exception {
        clients.inMemory()
                .withClient("handleCilentId")//客户端id
                .secret(passwordEncoder.encode("secret"))//客户端密钥
                .authorizedGrantTypes("authorization_code")//授权码模式
                .scopes("user_info") //授权范围
                .autoApprove(true)//开启自动授权
                .redirectUris("http://localhost:8881/success") //认证成功重定向
                .redirectUris("http://localhost:8881/login")
                .accessTokenValiditySeconds(10);//设置超时时间
    }
}
