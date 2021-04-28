package io.github.syske.springbootswagger2demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

// 无数据库启动
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class SpringBootSwagger2DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootSwagger2DemoApplication.class, args);
    }

}
