/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.springbootlearning.config;

import io.github.syske.springbootlearning.config.condition.ResultCondition;
import io.github.syske.springbootlearning.entity.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * 条件配置
 *
 * @author syske
 * @version 1.0
 * @date 2021-09-22 8:32
 */
@Configuration
public class ConditionConfig {
    private final Logger logger = LoggerFactory.getLogger(ConditionConfig.class);

    @Conditional(ResultCondition.class)
    @Bean
    public Result result() {
        logger.info("condition result");
        return new Result();
    }
}
