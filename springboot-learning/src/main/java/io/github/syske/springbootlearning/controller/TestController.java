package io.github.syske.springbootlearning.controller;

import io.github.syske.springbootlearning.controller.service.TestService;
import io.github.syske.springbootlearning.entity.Result;
import io.github.syske.springbootlearning.exception.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
@ResponseBody
public class TestController {

    @Autowired
    private TestService testService;

    @RequestMapping("/test1")
    public Result getTest() {
        return Result.getSuccess(true);
    }

    @RequestMapping("/test2")
    public void getTest2() {
        throw new MyException("test2");
    }

    @RequestMapping("/test3")
    public Result getTest3() {
        return testService.test3();
    }
}
