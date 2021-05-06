package io.igthub.syske.springbootstrategypattern.service;

import io.igthub.syske.springbootstrategypattern.entity.WrapperRequest;
import io.igthub.syske.springbootstrategypattern.entity.WrapperResponse;

/**
 * 业务公共接口
 */
public interface Myservice<T> {

    /**
     * 接口业务方法
     *
     * @param request
     * @return
     */
    WrapperResponse<T> handler(WrapperRequest<Object> request);

}
