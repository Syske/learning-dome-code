package io.igthub.syske.springbootstrategypattern.common.util;

import io.igthub.syske.springbootstrategypattern.service.business.HandlerContentService;

import java.util.Map;

public class HandlerContext {
    private Map<String, Class> handlerMap;

    public HandlerContext(Map<String, Class> handlerMap) {
        this.handlerMap = handlerMap;
    }

    public HandlerContentService getInstance(String type) {
        Class clazz = handlerMap.get(type);
        if (clazz == null) {
            throw new IllegalArgumentException("输入的参数类型有问题：" + type);
        }
        return (HandlerContentService) BeanTool.getBean(clazz);
    }
}
