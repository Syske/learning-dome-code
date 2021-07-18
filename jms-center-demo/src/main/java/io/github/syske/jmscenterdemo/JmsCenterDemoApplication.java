package io.github.syske.jmscenterdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class JmsCenterDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(JmsCenterDemoApplication.class, args);
    }

}
