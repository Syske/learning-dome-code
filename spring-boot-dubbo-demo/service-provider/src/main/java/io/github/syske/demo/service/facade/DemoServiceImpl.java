/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.demo.service.facade;

import io.github.syske.common.facade.DemoService;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * demo服务实现
 *
 * @author syske
 * @version 1.0
 * @date 2021-08-11 8:35
 */
//@DubboService(version = "1.0", interfaceName = "demoService", interfaceClass = DubboService.class)
@DubboService
public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello(String name) {
        System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Hello " + name +
                ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
        return "Hello " + name + ", response from provider: " + RpcContext.getContext().getLocalAddress();
    }
}
