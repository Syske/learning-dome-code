package io.github.syske.springbootbeanlisttest.service.impl;

import io.github.syske.springbootbeanlisttest.service.TestInterface;
import org.springframework.stereotype.Service;

/**
 * @program: springboot-bean-list-test
 * @description: 测试接口1
 * @author: syske
 * @date: 2022-06-06 18:19
 */
@Service("Test3")
public class Test3Impl implements TestInterface {
    @Override
    public String hello() {
        return "Test3Impl";
    }
}
