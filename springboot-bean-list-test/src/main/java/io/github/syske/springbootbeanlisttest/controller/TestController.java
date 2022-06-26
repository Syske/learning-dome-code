package io.github.syske.springbootbeanlisttest.controller;

import io.github.syske.springbootbeanlisttest.listener.event.SyskeApplicationEvent;
import io.github.syske.springbootbeanlisttest.service.TestInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: springboot-bean-list-test
 * @description: 测试
 * @author: syske
 * @date: 2022-06-06 18:20
 */
@RestController
public class TestController {

    @Autowired
    private List<TestInterface> testInterfaces;

    @Autowired
    private ApplicationContext applicationContext;

    @GetMapping("/test")
    public Object test() {
        int size = testInterfaces.size();
        System.out.println(size);
        return size;
    }

    @GetMapping("/event")
    public Object testEvent() {
        SyskeApplicationEvent syskeApplicationEvent = new SyskeApplicationEvent("test");
        syskeApplicationEvent.setEventName("sysk-event");
        syskeApplicationEvent.setEventBody("sysk-body");
        applicationContext.publishEvent(syskeApplicationEvent);
        return "success";
    }
}
