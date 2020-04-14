package io.github.syske.springbootjwtdemo.shiro.realm;

import io.github.syske.springbootjwtdemo.dao.entity.User;
import io.github.syske.springbootjwtdemo.sevice.UserService;
import io.github.syske.springbootjwtdemo.shiro.JwtToken;
import io.github.syske.springbootjwtdemo.sevice.JwtService;
import io.github.syske.springbootjwtdemo.util.CommonConstant;
import io.github.syske.springbootjwtdemo.util.JwtUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @program: springboot-jwt-demo
 * @description:
 * @author: syske
 * @create: 2020-03-13 18:36
 */
@Service
public class JwtShiroRealm extends AuthorizingRealm  {


    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = JwtUtil.getUsername(principals.toString());
        User user = userService.getUser(username);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRole(user.getRole());
        Set<String> permission = new HashSet<>(Arrays.asList(user.getPermission().split(",")));
        simpleAuthorizationInfo.addStringPermissions(permission);
        return simpleAuthorizationInfo;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        if (token == null) {
            throw new AuthenticationException("jwtToken 不允许为空");
        }
        // 解密获得username，用于和数据库进行对比
        String username = JwtUtil.getUsername(CommonConstant.PREFIX_USER_TOKEN + token);
        if (StringUtils.isEmpty(username)) {
            throw new AuthenticationException("token invalid");
        }

        User userBean = userService.getUser(username);
        if (userBean == null) {
            throw new AuthenticationException("User didn't existed!");
        }

        if (jwtService.checkJwt(token).getCode() == 0) {
            throw new AuthenticationException("Username or password error");
        }

        return new SimpleAuthenticationInfo(token, token, "my_realm");
    }

}
