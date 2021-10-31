package io.github.syske.demo.service.consumer.callback.impl;

import io.github.syske.demo.service.consumer.callback.CallBackDemoSevice;
import org.springframework.stereotype.Service;

/**
 * @program: spring-boot-dubbo-demo
 * @description: 回调方法实现
 * @author: syske
 * @date: 2021-08-17 10:30
 */
@Service("callBackDemoService")
public class CallBackDemoServiceImpl implements CallBackDemoSevice {
    @Override
    public void oninvoke(String name) {
        System.out.println("oninvoke name =" + name);
    }

    @Override
    public String onreturn(String response) {
        System.out.println("onreturn response =" + response);
        return "onreturn" + response;
    }

    @Override
    public void onthrow(Throwable t) {
        System.err.println("onthrow Throwable =" + t);
    }
}
