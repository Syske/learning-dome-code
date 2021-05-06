package io.github.syske.sofabootrpcdemo2;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import io.github.syske.sofabootrpcdemo2.facade.HelloService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SofabootRpcDemo2ApplicationTests {

    @Test
    void contextLoads() {
        int i = 0;
        long start = System.currentTimeMillis();
        while (i < 10000) {
            System.out.println(sayClientAnnotation("test"));
            i++;
        }
        long stop = System.currentTimeMillis();
        System.out.println("用时：" + (stop - start));
    }

    @SofaReference(interfaceType = HelloService.class, binding = @SofaReferenceBinding(bindingType = "bolt"))
    private HelloService helloService;


    public String sayClientAnnotation(String str) {

        String result = helloService.sayHello(str);

        return result;
    }
}
