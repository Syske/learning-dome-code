package io.github.syske.springbootdruidmoredatasources.config.datasource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import com.alibaba.druid.pool.DruidDataSource;


/**
 * @program: springboot-druid-more-datasources
 * @description: 主数据源配置类
 * @author: syske
 * @create: 2020-04-21 16:30
 */
@ConfigurationProperties(prefix = "spring.datasource.druid.primary")
public class PrimaryDataSourceWrapper extends DruidDataSource implements InitializingBean {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    private String url;

    private String driverClassName;

    private String connectionProperties;

    private String passwordCallbackClassName;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String getDriverClassName() {
        return driverClassName;
    }

    @Override
    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }

    public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }

    public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }

    @Override
    public void setConnectionProperties(String connectionProperties) {
        this.connectionProperties = connectionProperties;
    }

    public String getConnectionProperties() {
        return connectionProperties;
    }

    @Override
    public void setPasswordCallbackClassName(String passwordCallbackClassName) {
        this.passwordCallbackClassName = passwordCallbackClassName;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 如果未找到前缀“spring.datasource.druid”JDBC属性，将使用“Spring.DataSource”前缀JDBC属性。
        super.setUrl(url);
        super.setUsername(username);
        super.setPassword(password);
        super.setDriverClassName(driverClassName);
        super.setInitialSize(initialSize);
        super.setMinIdle(minIdle);
        super.setMaxActive(maxActive);
        super.setMaxWait(maxWait);
        super.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        super.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        super.setValidationQuery(validationQuery);
        super.setTestWhileIdle(testWhileIdle);
        super.setTestOnBorrow(testOnBorrow);
        super.setTestOnReturn(testOnReturn);
        super.setPoolPreparedStatements(poolPreparedStatements);
        super.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        super.setConnectionProperties(connectionProperties);
        super.setDbType(dbType);
        super.setPasswordCallbackClassName(passwordCallbackClassName);
    }

}