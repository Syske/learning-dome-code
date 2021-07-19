package io.github.syske.springbootsratertest;

import io.github.syske.starter.demo.service.JmsMessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootSraterTestApplicationTests {

    @Autowired
    private JmsMessageService jmsMessageService;

    @Test
    void contextLoads() {
        jmsMessageService.sendMessage("spring_boot_starter", "hello spring-boot-start");
    }

}
