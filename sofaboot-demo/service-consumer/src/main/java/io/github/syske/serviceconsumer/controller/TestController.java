package io.github.syske.serviceconsumer.controller;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import io.github.syske.sofa.facade.HelloService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: sofaboot-demo
 * @description: test
 * @author: syske
 * @create: 2021-04-11 10:23
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @SofaReference(uniqueId = "annotationImpl")
    private HelloService helloService;

    @GetMapping("/hello")
    public String hello(String name) {
        return helloService.sayHi(name);
    }
}
