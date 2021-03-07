package io.github.syske.springbootnacosdiscoverydemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SpringbootNacosDiscoveryDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootNacosDiscoveryDemoApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
