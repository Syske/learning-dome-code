/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.springbootcounterlimiter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 计数器限流
 *
 * @author syske
 * @version 1.0
 * @date 2021-10-31 21:29
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CounterLimit {

    /**
     * 调用方唯一key的名字
     *
     * @return
     */
    String name();
    /**
     * 限制访问次数
     * @return
     */
    int limitTimes();

    /**
     * 限制时长，也就是计数器的过期时间
     *
     * @return
     */
    long timeout();

    /**
     * 限制时长单位
     *
     * @return
     */
    TimeUnit timeUnit();

}
