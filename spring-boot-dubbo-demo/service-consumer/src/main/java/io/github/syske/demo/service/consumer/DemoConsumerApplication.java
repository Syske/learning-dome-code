/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.demo.service.consumer;

import io.github.syske.common.facade.DemoService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ConfigCenterConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author syske
 * @version 1.0
 * @date 2021-08-11 8:53
 */
@SpringBootApplication
@EnableDubbo
//@DubboComponentScan(value = "io.github.syske.common.facade")
public class DemoConsumerApplication {


    public static void main(String[] args) {
        SpringApplication.run(DemoConsumerApplication.class, args);
     /*   ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("consumer.xml");
        context.start();
        DemoService demoService = (DemoService) context.getBean("demoService");
        String hello = demoService.sayHello("world");
        System.out.println(hello);*/
    }
}
