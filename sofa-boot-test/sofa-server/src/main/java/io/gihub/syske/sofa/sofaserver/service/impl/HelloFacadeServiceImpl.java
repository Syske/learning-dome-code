/* Copyright © 2022 syske. All rights reserved. */
package io.gihub.syske.sofa.sofaserver.service.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import io.github.syske.sofa.facade.model.request.BaseRequest;
import io.github.syske.sofa.facade.model.result.HelloBaseResult;
import io.github.syske.sofa.facade.model.result.HelloDataResult;
import io.github.syske.sofa.facade.service.HelloFacadeService;

/**
 * 接口实现
 *
 * @author syske
 * @version 1.0
 * @date 2022-11-06 22:51
 */
@SofaService(interfaceType = HelloFacadeService.class, bindings = {@SofaServiceBinding(bindingType = "bolt")})
public class HelloFacadeServiceImpl implements HelloFacadeService {

    @Override
    public HelloBaseResult getBaseHello(BaseRequest request) {
        HelloBaseResult result = new HelloBaseResult();
        result.setId(123123123L);
        result.setName("hello");
        return result;
    }

    @Override
    public HelloDataResult getDataHello(BaseRequest request) {
        HelloDataResult result = new HelloDataResult();
        result.setId(123123123L);
        result.setName("hello");
        return result;
    }
}
