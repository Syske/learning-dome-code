package io.github.syske.springboot.activemq.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
@EnableJms
public class SpringbootActivemqDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringbootActivemqDemoApplication.class, args);
    }
}
