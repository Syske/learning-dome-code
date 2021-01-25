package io.github.syske.could;

/**
 * @program: spring-Cloud-dome
 * @description:
 * @author: syske
 * @create: 2019-12-11 18:03
 */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
public class ConsumerFeignApplication {

    public static void main(String[] args) {
        SpringApplication.run( ConsumerFeignApplication.class, args );
    }
}
