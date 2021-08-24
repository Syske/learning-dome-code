/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.demo.service.consumer.callback;

import io.github.syske.common.facade.callback.Notify;
import org.springframework.stereotype.Component;

/**
 * @author syske
 * @version 1.0
 * @date 2021-08-17 8:03
 */
@Component("demoCallBackService")
public class DemoCallBackService implements Notify {
    @Override
    public void onreturn(String msg) {
        System.out.println(String.format("返回结果: %s",  msg ));
    }

    @Override
    public void onthrow(Throwable ex) {
        System.out.println(String.format("错误信息：%s", ex));
    }

    @Override
    public void oninvoke(String msg) {
        System.out.println(String.format("oninvoke：%s", msg));
    }
}
