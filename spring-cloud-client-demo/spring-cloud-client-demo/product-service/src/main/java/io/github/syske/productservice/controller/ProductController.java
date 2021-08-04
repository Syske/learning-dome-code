/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.productservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * 产品
 *
 * @author syske
 * @version 1.0
 * @date 2021-08-02 8:35
 */
@RestController
public class ProductController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/ribbon")
    public Object queryUserByProductId() {
        List<JSONObject> jsonObjectList = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            JSONObject forObject = restTemplate.getForObject("http://user-center/user/" + (i + 1), JSONObject.class);
            jsonObjectList.add(forObject);
        }
        return jsonObjectList;
    }

    @RequestMapping("/testHystrix")
    public Object testHystrix() {
        List<JSONObject> jsonObjectList = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            jsonObjectList.add(restTemplate.getForObject("http://spring-cloud-Hystrix-demo/hystrix/" + i, JSONObject.class));
        }
        return jsonObjectList;
    }
}
