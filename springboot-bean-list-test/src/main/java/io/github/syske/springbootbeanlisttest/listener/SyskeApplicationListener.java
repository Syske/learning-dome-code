package io.github.syske.springbootbeanlisttest.listener;

import io.github.syske.springbootbeanlisttest.listener.event.SyskeApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * @program: springboot-bean-list-test
 * @description: 事件监听器
 * @author: syske
 * @date: 2022-06-23 18:12
 */
public class SyskeApplicationListener implements ApplicationListener<SyskeApplicationEvent> {
    @Override
    public void onApplicationEvent(SyskeApplicationEvent event) {
        String eventName = event.getEventName();
        System.out.println(eventName);
        Object eventBody = event.getEventBody();
        System.out.println(eventBody);
    }
}
