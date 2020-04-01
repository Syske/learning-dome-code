package io.githu.sysket.springbootshirocasdemo.config;

import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.AuthenticationStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.cas.CasFilter;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @program: springboot-shiro-cas-demo
 * @description: shiro配置类
 * @author: syske
 * @create: 2020-03-09 21:35
 */
@Configuration
public class ShiroConfig {

    @Bean(name = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(WebSecurityManager webSecurityManager,
                                                         @Value("${cas.server-url}") String casServerUrl,
                                                         @Value("${cas.service}") String casService) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(webSecurityManager);
        //拦截器.
        Map<String, Filter> filters = new HashMap<>();
        filters.put("cas", casFilter());
        shiroFilter.setFilters(filters);
        /**
         * 配置哪些页面需要受保护.
         * 以及访问这些页面需要的权限.
         * 1). anon 可以被匿名访问
         * 2). authc 必须认证(即登录)后才可能访问的页面.
         * 3). logout 登出.
         * 4). roles 角色过滤器
         * 5). cas 是指这个URL会被filters中的拦截器拦截
         */
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/static/**", "anon");
        /**
         * 这个拦截路径要和casService的地址匹配
         */
        filterChainDefinitionMap.put("/cas", "cas");
        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/logout", "logout");
        //<!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        filterChainDefinitionMap.put("/**", "authc");
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilter.setLoginUrl(casServerUrl + "?service=" + casService + "/cas");
        // 登录成功后要跳转的链接
        shiroFilter.setSuccessUrl("/");
        //未授权界面;
        shiroFilter.setUnauthorizedUrl("/403");
        shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilter;
    }

    @Bean
    public WebSecurityManager webSecurityManager(Realm realm, RememberMeManager rememberMeManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        securityManager.setRememberMeManager(rememberMeManager);
        return securityManager;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public CasFilter casFilter() {
        CasFilter casFilter = new CasFilter();
        casFilter.setSuccessUrl("/");
        casFilter.setFailureUrl("/403");
        return casFilter;
    }

    /**
     * 注解@DependsOn 让当前的bean在某个bean启动后启动
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public Realm realm(@Value("${cas.server-url}") String casServerUrl,
                       @Value("${cas.service}") String casService) {
        LoginUserCasRealm realm = new LoginUserCasRealm();
        realm.setAuthenticationCachingEnabled(true);
        realm.setAuthenticationCacheName("authenticationCache");
        realm.setAuthorizationCacheName("authorizationCache");
        realm.setCasServerUrlPrefix(casServerUrl);
        realm.setCasService(casService + "/cas");
        return realm;
    }

    @Bean
    public Cookie cookie() {
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie cookie = new SimpleCookie("JSID");
        //<!-- 记住我cookie生效时间30天 ,单位秒;-->
        cookie.setMaxAge(2592000);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        return cookie;
    }

    @Bean
    public CookieRememberMeManager rememberMeManager(Cookie cookie) {
        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
        rememberMeManager.setCookie(cookie);
        //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
        rememberMeManager.setCipherKey(Base64.decode("3AvVhmFLUs0KTA3Kprsdag=="));
        return rememberMeManager;
    }

    @Bean
    public AuthenticationStrategy authenticationStrategy() {
        return new AtLeastOneSuccessfulStrategy();
    }

    @Bean
    public Authenticator authenticator(AuthenticationStrategy authenticationStrategy) {
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        authenticator.setAuthenticationStrategy(authenticationStrategy);
        return authenticator;
    }

    @Bean
    public SessionDAO sessionDAO(SessionIdGenerator sessionIdGenerator) {
        EnterpriseCacheSessionDAO sessionDAO = new EnterpriseCacheSessionDAO();
        sessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
        sessionDAO.setSessionIdGenerator(sessionIdGenerator);
        return sessionDAO;
    }

    @Bean
    public SessionIdGenerator sessionIdGenerator() {
        return new JavaUuidSessionIdGenerator();
    }

    @Bean
    public SessionManager sessionManager(SessionDAO sessionDAO, Cookie cookie) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setGlobalSessionTimeout(30 * 60 * 1000);
        sessionManager.setSessionDAO(sessionDAO);
        sessionManager.setSessionIdCookie(cookie);
        return sessionManager;
    }
}
