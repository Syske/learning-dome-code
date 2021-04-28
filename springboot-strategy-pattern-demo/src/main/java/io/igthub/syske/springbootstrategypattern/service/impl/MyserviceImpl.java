package io.igthub.syske.springbootstrategypattern.service.impl;

import io.igthub.syske.springbootstrategypattern.common.util.HandlerContext;
import io.igthub.syske.springbootstrategypattern.entity.RequestHeader;
import io.igthub.syske.springbootstrategypattern.entity.WrapperRequest;
import io.igthub.syske.springbootstrategypattern.entity.WrapperResponse;
import io.igthub.syske.springbootstrategypattern.service.Myservice;
import io.igthub.syske.springbootstrategypattern.service.business.HandlerContentService;
import io.igthub.syske.springbootstrategypattern.service.business.impl.Handler01001ContentService;
import io.igthub.syske.springbootstrategypattern.service.business.impl.Handler01002ContentService;
import io.igthub.syske.springbootstrategypattern.service.business.impl.Handler01003ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 业务接口实现类
 */
@Service
public class MyserviceImpl implements Myservice {

    @Autowired
    private HandlerContext handlerContext;

    @Autowired
    private Handler01001ContentService service1;
    @Autowired
    private Handler01002ContentService service2;
    @Autowired
    private Handler01003ContentService service3;

    @Override
    public WrapperResponse handler(WrapperRequest request) {
        RequestHeader header = request.getHeader();
        if (header != null) {
            String businessCode = header.getBusinessCode();
            /*if("01001".equals(businessCode)) {
                // 处理业务01001
               return service1.handler(request);
            } else if("01002".equals(businessCode)) {
                // 处理业务01002
                return service2.handler(request);
            } else if("01003".equals(businessCode)) {
                //  处理业务01001
                return service3.handler(request);
            }*/
            HandlerContentService instance = handlerContext.getInstance(businessCode);
            return instance.handler(request);

        }
        return new WrapperResponse();
    }
}
