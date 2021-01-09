package io.github.syske.springbootjdbcmybatisdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("io.github.syske.springbootjdbcmybatisdemo.dao")
public class SpringbootJdbcMybatisDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootJdbcMybatisDemoApplication.class, args);
    }

}
