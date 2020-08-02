package io.github.syske.customannotationdemo.controller;

import io.github.syske.customannotationdemo.annotation.CheckAuth;
import io.github.syske.customannotationdemo.entity.ResultJson;
import io.github.syske.customannotationdemo.entity.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @RequestMapping("/test1")
    @CheckAuth
    public ResultJson test1() {
        ResultJson resultJson = new ResultJson();
        User user = new User();
        user.setId("1");
        user.setUsername("test");
        resultJson.setSuccess(user);
        return resultJson;
    }

    @RequestMapping("/test2")
    public ResultJson test2() {
        ResultJson resultJson = new ResultJson();
        User user = new User();
        user.setId("3");
        user.setUsername("test3");
        resultJson.setSuccess(user);
        return resultJson;
    }
}
