/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.usercenter.controller;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户
 *
 * @author syske
 * @version 1.0
 * @date 2021-08-02 8:20
 */
@RestController
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/user/{id}")
    public JSONObject getUserById(@PathVariable(name = "id") Long id) {
        List<ServiceInstance> instances = discoveryClient.getInstances("user-center");
        logger.info("instances = {}",  instances);
        JSONObject user = new JSONObject();
        user.put("id", id);
        user.put("name", "syske");
        return user;
    }
}
