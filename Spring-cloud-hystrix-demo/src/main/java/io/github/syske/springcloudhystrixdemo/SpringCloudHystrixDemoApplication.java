package io.github.syske.springcloudhystrixdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@SpringBootApplication
@EnableCircuitBreaker
public class SpringCloudHystrixDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudHystrixDemoApplication.class, args);
    }

}
