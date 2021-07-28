/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.springwebfluxdemo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * webflux测试
 *
 * @author syske
 * @version 1.0
 * @date 2021-07-28 8:30
 */
@RestController
@RequestMapping("/webflux")
public class WebFluxController {

    @RequestMapping("/hi")
    public Object sayHi(String name) {
        return "hi, " + name;
    }
}
