package io.github.syske.sofabootrpcdemo2.provider;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import io.github.syske.sofabootrpcdemo2.facade.HelloService;
import org.springframework.stereotype.Service;

/**
 * @program: sofaboot-rpc-demo2
 * @description: hello服务实现类
 * @author: syske
 * @create: 2021-04-11 10:59
 */
@SofaService(interfaceType = HelloService.class, bindings = { @SofaServiceBinding(bindingType = "bolt") })
@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        return name + ", hello!";
    }
}
