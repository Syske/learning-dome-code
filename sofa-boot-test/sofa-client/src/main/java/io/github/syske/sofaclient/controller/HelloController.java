/* Copyright © 2022 syske. All rights reserved. */
package io.github.syske.sofaclient.controller;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import io.github.syske.sofa.facade.model.request.BaseRequest;
import io.github.syske.sofa.facade.service.HelloFacadeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * controller
 *
 * @author syske
 * @version 1.0
 * @date 2022-11-06 22:58
 */
@RestController
public class HelloController {

    @SofaReference(interfaceType = HelloFacadeService.class, binding = @SofaReferenceBinding(bindingType = "bolt"))
    private HelloFacadeService helloFacadeService;

    @GetMapping("/base")
    public Object testBase() {
        BaseRequest request = new BaseRequest();
        request.setType("base");
        return helloFacadeService.getBaseHello(request);
    }

    @GetMapping("/data")
    public Object testData() {
        BaseRequest request = new BaseRequest();
        request.setType("data");
        return helloFacadeService.getDataHello(request);
    }
}
