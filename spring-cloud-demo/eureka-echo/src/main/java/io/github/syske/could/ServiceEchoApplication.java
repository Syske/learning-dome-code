package io.github.syske.could;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: spring-Cloud-dome
 * @description:
 * @author: syske
 * @create: 2019-12-11 17:47
 */
@SpringBootApplication
@EnableEurekaClient
@RestController
public class ServiceEchoApplication {

    public static void main(String[] args) {
        SpringApplication.run( ServiceEchoApplication.class, args );
    }

    @Value("${server.port}")
    String port;

    @RequestMapping("/echo")
    public String home(@RequestParam(value = "name") String name) {
        return "hello " + name + " ,this is port:" + port;
    }

}