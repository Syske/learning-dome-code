package io.github.syske.springbootwebservicedemo.service.impl;

import io.github.syske.springbootwebservicedemo.service.HelloService;
import org.springframework.stereotype.Service;

import javax.jws.WebService;

/**
 * @program: springboot-webservice-demo
 * @description: 测试接口实现类
 * @author: syske
 * @create: 2020-04-28 12:23
 */
@Service
@WebService(serviceName = "HelloService", // 与接口中指定的name一致
        targetNamespace = "http://service.ws.sample/", // 与接口中的命名空间一致,一般是接口的包名倒
        endpointInterface = "io.github.syske.springbootwebservicedemo.service.HelloService" // 接口地址
)
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        return "hello," + name;
    }
}
