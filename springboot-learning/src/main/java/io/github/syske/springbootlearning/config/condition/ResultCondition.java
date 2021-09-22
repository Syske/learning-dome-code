/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.springbootlearning.config.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * 条件配置
 *
 * @author syske
 * @version 1.0
 * @date 2021-09-22 8:35
 */
public class ResultCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Boolean matches = false;
        System.out.println("ResultCondition matches =" + matches);
        return matches;
    }
}
