package io.github.syske.springbootbeanlisttest.environment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @program: springboot-bean-list-test
 * @description:
 * @author: syske
 * @date: 2022-06-23 18:30
 */
public class SyskeEnvironmentPostProcessor implements EnvironmentPostProcessor {
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        System.out.println("syske postProcessEnvironment ");
    }
}
