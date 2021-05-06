package io.github.syske.sofabootdemo;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import io.github.syske.sofabootdemo.service.HelloService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class SofabootDemoApplicationTests {

    @Test
    void contextLoads() {
        int i = 0;
        long start = System.currentTimeMillis();
        while(i < 10000) {
            System.out.println(sayClientAnnotation("test"));
            i++;
        }
        long stop = System.currentTimeMillis();
        System.out.println("用时：" + (stop - start));
    }

    @SofaReference(interfaceType = HelloService.class, binding = @SofaReferenceBinding(bindingType = "bolt"))
    private HelloService helloService;


    public String sayClientAnnotation(String str) {

        String result = helloService.saySync(str);

        return result;
    }
}
