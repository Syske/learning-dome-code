/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.springbootlearning.initializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 自定义容器初始化类
 *
 * @author syske
 * @version 1.0
 * @date 2021-09-15 8:02
 */
public class SyskeInitializer implements ApplicationContextInitializer {
    private Logger logger = LoggerFactory.getLogger(SyskeInitializer.class);
    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        logger.info("====================start======================");
        logger.info("SyskeInitializer initialize方法被执行……");
        logger.info("ApplicationName: {}", configurableApplicationContext.getApplicationName());
        logger.info("isActive: {}", String.valueOf(configurableApplicationContext.isActive()));
        logger.info("====================end======================");
    }
}
