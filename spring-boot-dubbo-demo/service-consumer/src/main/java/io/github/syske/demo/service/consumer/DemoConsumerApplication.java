/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.demo.service.consumer;

import io.github.syske.common.facade.DemoService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ConfigCenterConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author syske
 * @version 1.0
 * @date 2021-08-11 8:53
 */
@SpringBootApplication
@DubboComponentScan
//@DubboComponentScan(value = "io.github.syske.common.facade")
public class DemoConsumerApplication {


    public static void main(String[] args) {
        SpringApplication.run(DemoConsumerApplication.class, args);
    }

//    @Bean
//    public ConfigCenterConfig configCenterConfig() {
//        ConfigCenterConfig configCenter = new   ConfigCenterConfig();
//        configCenter.setAddress("zookeeper://127.0.0.1:2181");
//        return configCenter;
//    }

    @Bean
    public ApplicationConfig applicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("dubbo-server");
        return applicationConfig;
    }

    @Bean
    public RegistryConfig registryConfig() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("zookeeper://127.0.0.1:2181");
        registryConfig.setClient("curator");
        return registryConfig;
    }
}
