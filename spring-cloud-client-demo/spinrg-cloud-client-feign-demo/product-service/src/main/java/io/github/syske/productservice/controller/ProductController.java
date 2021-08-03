/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.productservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import io.github.syske.productservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 产品controller
 *
 * @author syske
 * @version 1.0
 * @date 2021-08-03 7:58
 */
@RestController
public class ProductController {

    @Autowired
    private UserService userService;

    @GetMapping("/feign")
    public Object getUserPo() {
        List<JSONObject> userList = Lists.newArrayList();
        for (long i = 0; i < 10; i++) {
            userList.add(userService.getUser((i + 1)));
        }
        return userList;
    }
}
