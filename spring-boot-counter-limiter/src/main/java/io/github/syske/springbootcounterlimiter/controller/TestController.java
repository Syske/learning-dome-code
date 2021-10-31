/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.springbootcounterlimiter.controller;

import com.alibaba.fastjson.JSONObject;
import io.github.syske.springbootcounterlimiter.annotation.CounterLimit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author syske
 * @version 1.0
 * @date 2021-10-31 20:11
 */
@RestController
public class TestController {

    @CounterLimit(name = "token",limitTimes = 5, timeout = 60, timeUnit = TimeUnit.SECONDS)
    @GetMapping("/limit/count-test")
    public Object counterLimiter(String name, String token) {
        JSONObject result = new JSONObject();
       result.put("data", "success");
        return result;
    }

}
