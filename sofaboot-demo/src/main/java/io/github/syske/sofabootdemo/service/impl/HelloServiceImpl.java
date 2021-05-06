package io.github.syske.sofabootdemo.service.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import io.github.syske.sofabootdemo.service.IHelloService;
import org.springframework.stereotype.Service;

/**
 * @author syske
 * @date 2021-04-08 15:57
 */
@Service
@SofaService(interfaceType = IHelloService.class,
        bindings = {@SofaServiceBinding(bindingType = "bolt")})
public class HelloServiceImpl implements IHelloService {
    @Override
    public String sayHi(String name) {
        return "hi," + name;
    }
}
