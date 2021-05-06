package io.github.syske.springbootjwtdemo.config;

import io.github.syske.springbootjwtdemo.shiro.filter.JwtFilter;
import io.github.syske.springbootjwtdemo.shiro.realm.JwtShiroRealm;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @program: springboot-jwt-demo
 * @description: shiro配置类
 * @author: syske
 * @create: 2020-03-13 18:27
 */
@Configuration
public class ShiroConfig {

    @Bean("securityManager")
    public DefaultWebSecurityManager getManager(JwtShiroRealm jwtShiroRealm) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        // 使用自己的realm
        manager.setRealm(jwtShiroRealm);

        /*
         * 关闭shiro自带的session，详情见文档
         * http://shiro.apache.org/session-management.html#SessionManagement-StatelessApplications%28Sessionless%29
         */
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        manager.setSubjectDAO(subjectDAO);

        return manager;
    }

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean factory(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();

        factoryBean.setSecurityManager(securityManager);     //设置安全管理器
        factoryBean.setLoginUrl("/userLogin");//     如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        factoryBean.setSuccessUrl("/index"); //  登录成功后要跳转的链接
        factoryBean.setUnauthorizedUrl("/401");//未授权界面;
        // 添加自己的过滤器并且取名为jwt
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("jwt", new JwtFilter());
        factoryBean.setFilters(filterMap);


        /*
         * 自定义url规则
         * http://shiro.apache.org/web.html#urls-
         */
        Map<String, String> filterRuleMap = new LinkedHashMap<>();
        // 访问401和404页面不通过我们的Filter
        filterRuleMap.put("/401", "anon");
        filterRuleMap.put("/static/**", "anon");
        filterRuleMap.put("/css/**", "anon");
        filterRuleMap.put("/layui/**", "anon");
        filterRuleMap.put("/img/**", "anon");
        filterRuleMap.put("/js/**", "anon");
        filterRuleMap.put("/index", "anon");
        filterRuleMap.put("/login", "anon");
        filterRuleMap.put("/", "anon");
        filterRuleMap.put("/userLogin", "anon");
        filterRuleMap.put("/logout", "logout");//配置退出 过滤器,其中的具体的退出代码Shiro已经实现
        filterRuleMap.put("/**", "authc");//过滤链定义，从上向下顺序执行，一般将/**放在最为下边
        // 所有请求通过我们自己的JWT Filter
        filterRuleMap.put("/**", "jwt");

        factoryBean.setFilterChainDefinitionMap(filterRuleMap);
        return factoryBean;
    }

    /**
     * 下面的代码是添加注解支持
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        // 强制使用cglib，防止重复代理和可能引起代理出错的问题
        // https://zhuanlan.zhihu.com/p/29161098
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
