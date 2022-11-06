/* Copyright © 2022 syske. All rights reserved. */
package io.github.syske.springbootbeanlisttest.listener;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author syske
 * @version 1.0
 * @date 2022-08-30 22:58
 */
public class SyskeSourceApplicationListener implements ApplicationListener<ApplicationEvent> {
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        long timestamp = event.getTimestamp();
        System.out.println(timestamp);
    }
}
