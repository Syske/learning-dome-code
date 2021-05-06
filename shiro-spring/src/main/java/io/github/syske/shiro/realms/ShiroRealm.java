package io.github.syske.shiro.realms;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: shiro-spring
 * @description:
 * @create: 2019-10-20 22:12
 */
public class ShiroRealm extends AuthenticatingRealm {

    private static final transient Logger log = LoggerFactory.getLogger(AuthenticatingRealm.class);

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        log.info("doGetAuthenticationInfo toke" + token);
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        // 下面用到的用户名密码在实际应用中对应的是你数据库查到的用户信息，这里为了方便演示，没有配置数据库，关于收据库shiro整合，后期详细讲
        String username = usernamePasswordToken.getUsername();

        String password = null;

        if ("admin".equals(username)) {
            password = "admin";
        } else if ("user".equals(username)) {
            password = "user";
        } else {
            password = "123456";
        }

        if ("unkonw".equals(username)) {
            throw new UnknownAccountException("用户不存在！");
        }

        if ("locked".equals(username)) {
            throw new LockedAccountException("用户被锁定！");
        }

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, password, this.getName());
        return info;
    }

}
