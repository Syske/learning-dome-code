package io.github.syske.could;

/**
 * @program: spring-Cloud-dome
 * @description:
 * @author: syske
 * @create: 2019-12-11 17:50
 */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
//如果注册中心是EUREKA则只需要@EnableEurekaClient即可，如果是其他服务注册中心，则需要@EnableDiscoveryClient
@EnableHystrix
public class ConsumerRibbonApplication {

    public static void main(String[] args) {
        SpringApplication.run( ConsumerRibbonApplication.class, args );
    }

    @Bean
    @LoadBalanced //实现负载均衡
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
