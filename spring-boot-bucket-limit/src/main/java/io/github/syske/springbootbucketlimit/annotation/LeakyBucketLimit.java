/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.springbootbucketlimit.annotation;

import io.github.syske.springbootbucketlimit.bucket.LeakyBucket;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Objects;

/**
 * @author syske
 * @version 1.0
 * @date 2021-11-01 22:07
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LeakyBucketLimit {

    /**
     * 限流器名称
     * @return
     */
    String limitBeanName();

    /**
     * 拦截器class
     *
     * @return
     */
    Class<?> limitClass() default LeakyBucket.class;
}
