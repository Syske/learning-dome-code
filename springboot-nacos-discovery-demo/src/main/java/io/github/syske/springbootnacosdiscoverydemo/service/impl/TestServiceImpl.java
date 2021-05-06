package io.github.syske.springbootnacosdiscoverydemo.service.impl;

import io.github.syske.springbootnacosdiscoverydemo.service.TestService;
import org.springframework.stereotype.Service;

/**
 * @program: springboot-nacos-discovery-demo
 * @description: 测试服务实现类
 * @author: syske
 * @create: 2021-03-07 22:24
 */
@Service
public class TestServiceImpl implements TestService {
    @Override
    public String sayHello(String name) {
        return "hello, " + name;
    }
}
