package io.github.syske.resourceserverdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
public class ResourceServerDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResourceServerDemoApplication.class, args);
    }

}
