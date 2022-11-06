package io.github.syske.springbootbeanlisttest.listener.event;

import org.springframework.context.ApplicationEvent;

/**
 * @program: springboot-bean-list-test
 * @description: 事件
 * @author: syske
 * @date: 2022-06-23 18:12
 */
public class Syske2ApplicationEvent extends ApplicationEvent {
    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public Syske2ApplicationEvent(Object source) {
        super(source);
    }

    private String eventName;

    private Object eventBody;

    /**
     * 获取 eventName 的值
     *
     * @return 返回 eventName 的值
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * 设置 eventName 的值
     *
     * @param eventName eventName
     */
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    /**
     * 获取 eventBody 的值
     *
     * @return 返回 eventBody 的值
     */
    public Object getEventBody() {
        return eventBody;
    }

    /**
     * 设置 eventBody 的值
     *
     * @param eventBody eventBody
     */
    public void setEventBody(Object eventBody) {
        this.eventBody = eventBody;
    }

    @Override
    public String toString() {
        return "SyskeApplicationEvent{" +
                "eventName='" + eventName + '\'' +
                ", eventBody=" + eventBody +
                "} " + super.toString();
    }
}
