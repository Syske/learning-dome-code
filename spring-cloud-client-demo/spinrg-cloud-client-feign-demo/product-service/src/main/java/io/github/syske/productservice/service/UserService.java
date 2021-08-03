/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.productservice.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 用户信息接口
 * @author syske
 * @version 1.0
 * @date 2021-08-03 7:54
 */
@FeignClient("user")
public interface UserService {

    @GetMapping("/user/{id}")
    public JSONObject getUser(@PathVariable("id") Long id);
}
