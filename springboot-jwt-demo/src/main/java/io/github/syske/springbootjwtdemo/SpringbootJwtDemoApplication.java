package io.github.syske.springbootjwtdemo;

import io.github.syske.springbootjwtdemo.util.SpringContextUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
@EnableScheduling
@ServletComponentScan
public class SpringbootJwtDemoApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SpringbootJwtDemoApplication.class, args);
        SpringContextUtil.setApplicationContext(context);
    }

}
