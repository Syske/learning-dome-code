package io.github.syske;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @program: weblogic-monitor
 * @description:
 * @author: syske
 * @create: 2020-01-16 17:00
 */
@SpringBootApplication
@MapperScan("io.github.syske.dao.mapper")
public class WeblogicMonitor {
    public static void main(String[] args) {
        SpringApplication.run(WeblogicMonitor.class, args);
    }
}
