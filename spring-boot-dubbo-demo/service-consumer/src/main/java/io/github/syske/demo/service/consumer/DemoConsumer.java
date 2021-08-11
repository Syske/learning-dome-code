/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.demo.service.consumer;

import io.github.syske.common.facade.DemoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author syske
 * @version 1.0
 * @date 2021-08-11 8:53
 */
@SpringBootApplication
public class DemoConsumer {

    @DubboReference
    private DemoService demoService;

    RpcContext context = new RpcContext();

    public static void main(String[] args) {
        String hello = demoService.sayHello("world");
        System.out.println(hello);
    }
}
