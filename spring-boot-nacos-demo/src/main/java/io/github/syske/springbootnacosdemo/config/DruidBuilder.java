package io.github.syske.springbootnacosdemo.config;

/**
 * @program: springboot-druid-more-datasources
 * @description:
 * @author: syske
 * @create: 2020-04-21 16:39
 */

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * druid配置构建类
 * 备注: 如数据源的属性有不一致的情况,可把该类中的Bean单独创建一个类
 *
 * @author sysker
 */
@Configuration
public class DruidBuilder {

    /**
     * 实例化
     *
     * @return
     */
    public static DruidBuilder create() {
        return new DruidBuilder();
    }

    /**
     * 主数据源: 如果在使用时,不特别指定Bean的名称,默认是使用主数据源操作.
     *
     * @return
     * @Primary: 自动装配时当出现多个Bean时, 被注解为@Primary的Bean将作为首选者,否则将抛出异常 .
     * @ConfigurationProperties : 根据配置文件中prefix前缀的属性名称,批量注入属性值.
     */
    @Bean(name = "DataSource")
    public DataSource primaryDataSource() {
        // 第一种默认创建方式
        //DruidDataSource druidDataSource = DruidDataSourceBuilder.create().build();

        // 第二种手动创建方式
        DruidDataSource druidDataSource = DruidBuilder.create().buildPromaryDataSource();
        System.out.println("==>DataSource,druidDataSource: " + druidDataSource);
        return druidDataSource;
    }

    /**
     * 创建数据源
     *
     * @return
     */
    public DruidDataSource buildPromaryDataSource() {
        return new DataSourceWrapper();
    }


}
