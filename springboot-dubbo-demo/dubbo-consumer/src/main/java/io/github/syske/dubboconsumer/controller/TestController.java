package io.github.syske.dubboconsumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import io.github.syske.service.HelloService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: springboot-dubbo-demo
 * @description: 测试controller
 * @author: syske
 * @create: 2021-03-17 21:46
 */
@RestController
public class TestController {

    //dubbo注解，可以仔细了解下这个注解
    @Reference
    private HelloService service;

    @RequestMapping("/hello")
    public String sayHello(String name) {
        return service.sayHi(name);
    }
}

