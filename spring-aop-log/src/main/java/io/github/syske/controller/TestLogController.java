package io.github.syske.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: spring-ao-plog
 * @description: 日志记录测试controller
 * @author: liu yan
 * @create: 2019-11-30 22:06
 */
@Controller
@RequestMapping("/log")
public class TestLogController {

    @ApiOperation("测试日志记录")
    @RequestMapping(value = "/hello")
    @ResponseBody
    public String hello(String name) {
        return name + ",hello";
    }

}
