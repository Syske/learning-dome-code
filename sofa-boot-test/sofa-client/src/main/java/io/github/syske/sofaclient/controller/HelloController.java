/* Copyright © 2022 syske. All rights reserved. */
package io.github.syske.sofaclient.controller;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
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

    private HelloFacadeService helloFacadeService;
    @GetMapping("/test")
    public Object testData() {

        return null;
    }
}
