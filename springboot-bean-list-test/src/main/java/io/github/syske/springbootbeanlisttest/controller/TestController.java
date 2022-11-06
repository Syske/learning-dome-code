package io.github.syske.springbootbeanlisttest.controller;

import io.github.syske.springbootbeanlisttest.listener.event.Syske2ApplicationEvent;
import io.github.syske.springbootbeanlisttest.listener.event.SyskeApplicationEvent;
import io.github.syske.springbootbeanlisttest.service.TestInterface;
import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

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
    private Map<String, TestInterface> testInterfaceMap;

    @Autowired
    private ApplicationContext applicationContext;

    @GetMapping("/test")
    public Object test(String name) {
        int size = testInterfaces.size();
        System.out.println(testInterfaces.get(2).hello());
        TestInterface testInterface = testInterfaceMap.get(name);
        System.out.println(testInterface.hello());
        System.out.println(size);
        return size;
    }

    @GetMapping("/event")
    public Object testEvent() {
        SyskeApplicationEvent syskeApplicationEvent = new SyskeApplicationEvent("test");
        syskeApplicationEvent.setEventName("sysk-event");
        syskeApplicationEvent.setEventBody("sysk-body");
        applicationContext.publishEvent(syskeApplicationEvent);
        Syske2ApplicationEvent syskeApplicationEvent2 = new Syske2ApplicationEvent("test2");
        syskeApplicationEvent2.setEventName("sysk-event2");
        syskeApplicationEvent2.setEventBody("sysk-body2");

        applicationContext.publishEvent(syskeApplicationEvent2);
        return "success";
    }

    @GetMapping("/request-mapping")
    public Object requestTest(HttpServletRequest request) {
        String a = request.getParameter("a");
        System.out.println("a: " + a);
        new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String a1 = request.getParameter("a");
            System.out.println("a1: " + a1);
        }).start();
        System.out.println("a: " + a);
        return "success";
    }
}
