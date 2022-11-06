/* Copyright © 2022 syske. All rights reserved. */
package io.github.syske.sofa.facade.service;

import io.github.syske.sofa.facade.model.request.BaseRequest;
import io.github.syske.sofa.facade.model.result.HelloBaseResult;
import io.github.syske.sofa.facade.model.result.HelloDataResult;

/**
 * hello接口
 *
 * @author syske
 * @version 1.0
 * @date 2022-11-06 22:44
 */
public interface HelloFacadeService {

    HelloBaseResult getBaseHello(BaseRequest request);

    HelloDataResult getDataHello(BaseRequest request);
}
