package io.github.syske.sofabootdemo;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import io.github.syske.sofabootdemo.service.IHelloService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SofabootDemoApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(sayClientAnnotation("sofaboot"));
    }

    @SofaReference(interfaceType = IHelloService.class, binding = @SofaReferenceBinding(bindingType = "bolt"))
    private IHelloService iHelloService;

    public String sayClientAnnotation(String str) {

        String result = iHelloService.sayHi(str);

        return result;
    }
}
