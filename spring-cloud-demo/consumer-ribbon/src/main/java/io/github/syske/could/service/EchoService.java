package io.github.syske.could.service;

/**
 * @program: spring-Cloud-dome
 * @description:
 * @author: syske
 * @create: 2019-12-11 17:53
 */

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EchoService {

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "error")
    public String echo(String name) {
        return restTemplate.getForObject("http://EUREKA-ECHO/echo?name=" + name, String.class);
    }

    public String error(String name) {
        return "hi," + name + ",Service is error!";
    }


}