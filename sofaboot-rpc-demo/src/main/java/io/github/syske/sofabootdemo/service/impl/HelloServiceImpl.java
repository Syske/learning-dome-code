package io.github.syske.sofabootdemo.service.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import io.github.syske.sofabootdemo.service.HelloService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @program: sofaboot-demo
 * @description: helloService实现类
 * @author: syske
 * @create: 2021-04-08 22:35
 */
@SofaService(interfaceType = HelloService.class, bindings = { @SofaServiceBinding(bindingType = "bolt") })
@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public String saySync(String string) {
        return string;
    }
}
