package io.githu.sysket.springbootshirocasdemo.shiro.realm;

import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashSet;
import java.util.Set;

/**
 * @program: springboot-shiro-cas-demo
 * @description: 单点登陆realm
 * @author: syske
 * @create: 2020-03-09 21:41
 */
public class SsoCasRealm extends CasRealm {
        @Override
        protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
            SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
            Set<String> strings = new HashSet<>();
            strings.add("USER");
            //这里获取用户角色和权限的逻辑，每个项目不一样，所以没写实现代码
            //设置用户拥有的角色
            authorizationInfo.setRoles(strings);
            //设置用户拥有的权限
            authorizationInfo.setStringPermissions(strings);
            return authorizationInfo;
        }
}
