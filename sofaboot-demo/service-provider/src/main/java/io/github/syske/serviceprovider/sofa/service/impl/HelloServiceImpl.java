package io.github.syske.serviceprovider.sofa.service.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import io.github.syske.sofa.facade.HelloService;

/**
 * @program: sofaboot-demo
 * @description: hello实现类
 * @author: syske
 * @create: 2021-04-11 10:18
 */
@SofaService(uniqueId = "helloServiceImpl")
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHi(String name) {
        return name + ", hello";
    }
}
