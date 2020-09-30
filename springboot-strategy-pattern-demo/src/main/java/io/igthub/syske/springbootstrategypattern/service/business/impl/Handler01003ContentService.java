package io.igthub.syske.springbootstrategypattern.service.business.impl;

import io.igthub.syske.springbootstrategypattern.entity.WrapperRequest;
import io.igthub.syske.springbootstrategypattern.entity.WrapperResponse;
import io.igthub.syske.springbootstrategypattern.service.annotation.BusinessType;
import io.igthub.syske.springbootstrategypattern.service.business.HandlerContentService;
import org.springframework.stereotype.Service;

@Service
@BusinessType("01003")
public class Handler01003ContentService implements HandlerContentService {
    @Override
    public WrapperResponse handler(WrapperRequest request) {
        WrapperResponse<String> stringWrapperResponse = new WrapperResponse<>();
        stringWrapperResponse.setMessage("01003");
        System.out.println("01003业务被调用");
        return stringWrapperResponse;
    }
}
