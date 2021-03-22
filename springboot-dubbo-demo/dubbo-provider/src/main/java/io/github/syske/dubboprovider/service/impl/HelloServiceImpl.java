package io.github.syske.dubboprovider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import io.github.syske.service.HelloService;
import org.springframework.stereotype.Component;

/**
 * @program: springboot-dubbo-demo
 * @description: 测试服务实现
 * @author: syske
 * @create: 2021-03-17 21:41
 */
//@Service是dubbo里的注解，作用是暴露服务，不要选择spring的@Service
@Service
@Component
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHi(String name) {
        return "hello," + name;
    }
}
