package io.github.syske.springbootweblogicjndidemo.config;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jndi.JndiObjectFactoryBean;

/**
 * @program: springboot-weblogic-jndi-demo
 * @description: 数据源配置：weblogic的jndi数据源
 * @author: syske
 * @create: 2020-04-23 15:01
 */
@Configuration
public class DataSourceConfig {
    @Value("${spring.datasource.jndi-name}")
    private String JNDI_NAME;

        @Bean(name = "dataSource")
        public DataSource jndiDataSource() throws IllegalArgumentException, NamingException {

            JndiObjectFactoryBean bean = new JndiObjectFactoryBean();

            Properties properties = new Properties();
            properties.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
            properties.put(Context.PROVIDER_URL, "t3://127.0.0.1:6201");
            //weblogic账号
            properties.put(Context.SECURITY_PRINCIPAL, "weblogic");
            //weblogic密码
            properties.put(Context.SECURITY_CREDENTIALS, "weblogic2019");//

            bean.setJndiEnvironment(properties);

            bean.setResourceRef(true);
            bean.setJndiName(JNDI_NAME);
            bean.setProxyInterface(DataSource.class);
            bean.setLookupOnStartup(false);
            bean.afterPropertiesSet();

            return (DataSource) bean.getObject();

        }
}
