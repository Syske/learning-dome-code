package io.github.syske.springbootdruidmoredatasources.config.datasource;

/**
 * @program: springboot-druid-more-datasources
 * @description: druid数据源监控配置
 * @author: syske
 * @create: 2020-04-21 16:43
 */

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

/**
 * druid页面监控,添加该配置类,springboot启动之后可通过访问地址进行查看
 * 访问地址: http://localhost:8080/druid
 * 账号: admin
 * 密码: admin
 * @author syske
 */
@Configuration
public class DruidServletConfiguration {

    /**
     * 添加druid页面监控servlet
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        // 白名单
        Map<String,String> initParameters = new HashMap<>(16);
        // 禁用HTML页面上的“REST ALL”功能
        initParameters.put("resetEnable","false");
        // IP白名单（没有配置或者为空，则允许所有访问）
        initParameters.put("/druid/*","");
        // ip黑名单
        initParameters.put("deny","");
        // 监控页面登录用户名
        initParameters.put("loginUsername","admin");
        // 监控页面登录用户密码
        initParameters.put("loginPassword", "admin");

        registrationBean.setInitParameters(initParameters);
        return registrationBean;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        // 过滤规则
        filterRegistrationBean.addUrlPatterns("/*");
        // 忽略过滤格式
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");
        //filterRegistrationBean.addInitParameter("profileEnable", "true");
        //filterRegistrationBean.addInitParameter("principalCookieName", "USER_COOKIE");
        //filterRegistrationBean.addInitParameter("principalSessionName", "USER_SESSION");
        //filterRegistrationBean.addInitParameter("DruidWebStatFilter", "/*");
        return filterRegistrationBean;
    }

}
