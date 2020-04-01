package io.github.syske.could;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @program: spring-Cloud-dome
 * @description:
 * @author: syske
 * @create: 2019-12-11 17:40
 */
@SpringBootApplication
@EnableEurekaClient
public class ServiceClientApplication {

    public static void main(String[] args) {
        SpringApplication.run( ServiceClientApplication.class, args );
    }

}
