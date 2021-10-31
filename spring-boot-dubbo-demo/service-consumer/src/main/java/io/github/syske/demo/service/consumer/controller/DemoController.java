package io.github.syske.demo.service.consumer.controller;

import io.github.syske.common.facade.DemoService;
import org.apache.dubbo.common.constants.ClusterRules;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.Method;
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
    @DubboReference(version = "1.0", interfaceName = "demoService", retries = 4, check = false, interfaceClass = DemoService.class,
            url = "",loadbalance = "roundrobin", cluster =  ClusterRules.FAIL_FAST, methods = {@Method(name = "sayHello", oninvoke = "callBackDemoService.oninvoke")})
    private DemoService demoService;

    @RequestMapping("/test")
    public Object demo() {
        String hello = demoService.sayHello("world");
        System.out.println(hello);
        return hello;
    }
}
