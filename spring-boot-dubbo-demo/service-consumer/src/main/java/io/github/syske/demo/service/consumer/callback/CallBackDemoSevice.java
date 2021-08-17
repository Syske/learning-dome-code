package io.github.syske.demo.service.consumer.callback;

/**
 * @program: spring-boot-dubbo-demo
 * @description: 回调方法
 * @author: syske
 * @date: 2021-08-17 10:25
 */
public interface CallBackDemoSevice {
    void oninvoke(String name);

    String onreturn(String name);

    void onthrow(Throwable t);
}
