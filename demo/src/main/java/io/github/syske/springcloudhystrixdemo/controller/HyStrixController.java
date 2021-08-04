/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.springcloudhystrixdemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * 断路器测试controller
 *
 * @author syske
 * @version 1.0
 * @date 2021-08-04 7:46
 */
@RestController
public class HyStrixController {

    @RequestMapping("/hystrix/{name}")
    @HystrixCommand(fallbackMethod = "error")
    public Object hystrix(@PathVariable(name = "name") String name) {
        JSONObject jsonObject = new JSONObject();
        try {
            Double v = 3000 * Math.random();
            System.out.println("name: " + name + " 睡眠时间：" + v);
            jsonObject.put("sleep", v.longValue());
            jsonObject.put("name", name);
            jsonObject.put("message", "请求成功");
            Thread.sleep(v.longValue());
        } catch (Exception e) {
            System.out.println(e);
        }
        return jsonObject;
    }

    public Object error(String name) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", "触发服务熔断机制");
        jsonObject.put("name", name);
        return jsonObject;
    }
}
