package io.github.syske.mybatis.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.SpringApplication;
import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 *  动态数据源示例
 * @author syske
 */
@SpringBootApplication
@MapperScan("io.github.syske.mybatis.datasource.dao")
public class SpringMybatisDynamicDatasourceDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringMybatisDynamicDatasourceDemoApplication.class, args);
    }

    @Bean
    @Primary
    @ConfigurationProperties("default.datasource")
    public DataSourceProperties defaultDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.hikari")
    public DataSource defaultDataSource() {
        DataSourceProperties dataSourceProperties = defaultDataSourceProperties();
        return dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean(name = "polarDataSourceProperties")
    @ConfigurationProperties("polar.datasource")
    public DataSourceProperties polarDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource polarDataSource() {
        return polarDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

}
