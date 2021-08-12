package io.github.syske.demo.service.consumer.controller;

import io.github.syske.common.facade.DemoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: spring-boot-dubbo-demo
 * @description:
 * @author: syske
 * @date: 2021-08-11 12:43
 */
@RestController
@RequestMapping("/dubbo")
public class DemoController {
    @DubboReference
    private DemoService demoService;

    @RequestMapping("/test")
    public Object demo() {
        String hello = demoService.sayHello("world");
        System.out.println(hello);
        return hello;
    }
}
