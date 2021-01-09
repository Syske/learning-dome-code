package io.igthub.syske.springbootstrategypattern.service.business;

import io.igthub.syske.springbootstrategypattern.entity.WrapperRequest;
import io.igthub.syske.springbootstrategypattern.entity.WrapperResponse;

public interface HandlerContentService<T> {
    WrapperResponse<T> handler(WrapperRequest<Object> request);
}
