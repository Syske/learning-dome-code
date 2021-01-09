package io.igthub.syske.springbootstrategypattern.common.util;


import io.igthub.syske.springbootstrategypattern.service.annotation.BusinessType;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sysker
 * @date 2020/9/12 12:03
 * @Description: 该类主要指将扫描的信息注入applicationContext容器中
 */
@Component
public class HandlerProcessor implements BeanFactoryPostProcessor {

    /**
     * 扫描hanglerMap注解并注入容器中
     *
     * @param beanFactory
     * @throws BeansException
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Map<String, Class> handlerMap = new HashMap<>(3);
        ClassScanner.scan(Const.HANDLER_PACAGER, BusinessType.class).forEach(clazz -> {
            String type = clazz.getAnnotation(BusinessType.class).value();
            handlerMap.put(type, clazz);
        });
        HandlerContext context = new HandlerContext(handlerMap);
        beanFactory.registerSingleton(HandlerContext.class.getName(), context);
    }
}
