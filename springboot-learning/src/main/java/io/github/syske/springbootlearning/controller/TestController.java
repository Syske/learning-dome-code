package io.github.syske.springbootlearning.controller;

import io.github.syske.springbootlearning.service.TestService;
import io.github.syske.springbootlearning.entity.Result;
import io.github.syske.springbootlearning.exception.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequestMapping("/test")
@ResponseBody
public class TestController {

    @Autowired
    private TestService testService;

    @Autowired
    private ConversionService conversionService;

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

    @RequestMapping("/converter")
    public Object testConverter() {
        Date date = conversionService.convert("2021-09-16", Date.class);
        System.out.println(date);
        return date;
    }

    @RequestMapping("/login")
    public String login(String username, Date logindate, HttpSession session) {
        if(!StringUtils.isEmpty(username)) {
            session.setAttribute("username", username);
            System.out.println(logindate);
            return "index";
        }
        return "login";

    }
}
