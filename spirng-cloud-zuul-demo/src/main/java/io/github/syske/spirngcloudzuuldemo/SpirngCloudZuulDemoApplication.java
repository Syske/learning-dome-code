package io.github.syske.spirngcloudzuuldemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class SpirngCloudZuulDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpirngCloudZuulDemoApplication.class, args);
    }

}
