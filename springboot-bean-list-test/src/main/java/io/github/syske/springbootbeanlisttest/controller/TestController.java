package io.github.syske.springbootbeanlisttest.controller;

import io.github.syske.springbootbeanlisttest.service.TestInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: springboot-bean-list-test
 * @description: 测试
 * @author: syske
 * @date: 2022-06-06 18:20
 */
@RestController
public class TestController {

    @Autowired
    private List<TestInterface> testInterfaces;

    @GetMapping("/test")
    public Object test() {
        int size = testInterfaces.size();
        System.out.println(size);
        return size;
    }
}
