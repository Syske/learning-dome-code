package io.github.syske.springbootwebservicedemo.service.impl;

import io.github.syske.springbootwebservicedemo.service.Hello2Service;
import org.springframework.stereotype.Service;

import javax.jws.WebService;

/**
 * @program: springboot-webservice-demo
 * @description: 第二个webservice实现类
 * @author: syske
 * @create: 2020-04-28 14:46
 */
@Service
@WebService(serviceName = "Hello2Service", // 与接口中指定的name一致
        targetNamespace = "http://service.ws.sample/", // 与接口中的命名空间一致,一般是接口的包名倒
        endpointInterface = "io.github.syske.springbootwebservicedemo.service.Hello2Service" // 接口地址
)
public class Hello2ServiceImpl implements Hello2Service {
    @Override
    public String sayHello2(String myname) {
        return "hello2 ," + myname;
    }
}
