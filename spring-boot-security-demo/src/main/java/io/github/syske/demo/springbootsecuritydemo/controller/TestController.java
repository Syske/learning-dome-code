/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.demo.springbootsecuritydemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试
 *
 * @author syske
 * @version 1.0
 * @date 2021-07-20 7:44
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("security")
    public Object testSecurity(String name) {
        return "hello, " + name;

    }
}
