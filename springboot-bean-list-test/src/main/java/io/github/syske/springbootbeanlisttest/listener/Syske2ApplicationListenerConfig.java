package io.github.syske.springbootbeanlisttest.listener;

import io.github.syske.springbootbeanlisttest.listener.event.Syske2ApplicationEvent;
import io.github.syske.springbootbeanlisttest.listener.event.SyskeApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @program: springboot-bean-list-test
 * @description: 事件监听器
 * @author: syske
 * @date: 2022-06-23 18:12
 */
@Component
public class Syske2ApplicationListenerConfig {

    @EventListener
    public void doEvent2(Syske2ApplicationEvent event) {
        String eventName = event.getEventName();
        System.out.println(eventName);
        Object eventBody = event.getEventBody();
        System.out.println(eventBody);
        System.out.println("Syske2ApplicationEvent");
    }

    @EventListener
    public void doEvent(SyskeApplicationEvent event) {
        String eventName = event.getEventName();
        System.out.println(eventName);
        Object eventBody = event.getEventBody();
        System.out.println(eventBody);
        System.out.println("Syske3ApplicationEvent");
    }
}
