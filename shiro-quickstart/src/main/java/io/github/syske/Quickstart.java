package io.github.syske;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Simple Quickstart application showing how to use Shiro's API.
 *
 * @since 0.9 RC2
 */
public class Quickstart {

    private static final transient Logger log = LoggerFactory.getLogger(Quickstart.class);


    public static void main(String[] args) {
        // 创建SecurityManager工厂
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        // 通过工厂创建SecurityManager实例
        SecurityManager securityManager = factory.getInstance();
        // 将securityManager传给SecurityUtils
        SecurityUtils.setSecurityManager(securityManager);
        // 从SecurityUtils中获取Subject实例
        Subject currentUser = SecurityUtils.getSubject();
        /* 从Subject实例中获取Session实例
        这里需要说明的是shiro的Session非常强大，不仅可以在web中使用，而且可以在J2SE项目中使用，更重要的是在web项目中使用时，他会自动将HttpServerletSession自动整合到自己的session，让你直接可以在Shiro的session中拿到你放在HttpServerletSession中的变量，这在非controller组件中非常有用
        */
        Session session = currentUser.getSession();
        // 在session中放置变量
        session.setAttribute("someKey", "aValue");
        // 从session中取出变量
        String value = (String) session.getAttribute("someKey");
        if (value.equals("aValue")) {
            log.info("Retrieved the correct value! [" + value + "]");
        }
        // 判断用户（Subject）是否经过授权（登录）
        if (!currentUser.isAuthenticated()) {
            // 如果未登录，创建包含用户名及密码的认证令牌：用户名，密码
            UsernamePasswordToken token = new UsernamePasswordToken("lonestarr", "vespa");
            // 设置记住我标识，如果该标识为true，对于运行记住我访问的资源，不用经过登录认证即可访问
            token.setRememberMe(true);
            try {
                // Subject认证授权
                currentUser.login(token);
            } catch (UnknownAccountException uae) {
                // 用户名未知
                log.info("There is no user with username of " + token.getPrincipal());
            } catch (IncorrectCredentialsException ice) {
                // 密码错误
                log.info("Password for account " + token.getPrincipal() + " was incorrect!");
            } catch (LockedAccountException lae) {
                // 用户被锁定
                log.info("The account for username " + token.getPrincipal() + " is locked.  " +
                        "Please contact your administrator to unlock it.");
            } catch (AuthenticationException ae) {
                // 其他认证错误，AuthenticationException为其他认证异常的父类
            }
        }

        //say who they are:
        //print their identifying principal (in this case, a username):
        log.info("User [" + currentUser.getPrincipal() + "] logged in successfully.");

        // 判断用户是否拥有schwartz角色
        if (currentUser.hasRole("schwartz")) {
            log.info("May the Schwartz be with you!");
        } else {
            log.info("Hello, mere mortal.");
        }

        // 判断用户是否拥有lightsaber:wield权限
        if (currentUser.isPermitted("lightsaber:wield")) {
            log.info("You may use a lightsaber ring.  Use it wisely.");
        } else {
            log.info("Sorry, lightsaber rings are for schwartz masters only.");
        }

        // 同上，只是这里权限比较特殊
        if (currentUser.isPermitted("winnebago:drive:eagle5")) {
            log.info("You are permitted to 'drive' the winnebago with license plate (id) 'eagle5'.  " +
                    "Here are the keys - have fun!");
        } else {
            log.info("Sorry, you aren't allowed to drive the 'eagle5' winnebago!");
        }

        // 用户退出登录
        currentUser.logout();

        System.exit(0);
    }
}
