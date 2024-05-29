package io.gihub.syske.sofa.sofaserver.filter;

import com.alipay.sofa.rpc.core.exception.SofaRpcException;
import com.alipay.sofa.rpc.core.request.SofaRequest;
import com.alipay.sofa.rpc.core.response.SofaResponse;
import com.alipay.sofa.rpc.ext.Extension;
import com.alipay.sofa.rpc.filter.AutoActive;
import com.alipay.sofa.rpc.filter.Filter;
import com.alipay.sofa.rpc.filter.FilterInvoker;
import lombok.extern.slf4j.Slf4j;


@Extension(value = "myRpcFilter")
@AutoActive(providerSide = true, consumerSide = true)
@Slf4j
public class MyRpcFilter extends Filter {
    @Override
    public SofaResponse invoke(FilterInvoker filterInvoker, SofaRequest sofaRequest) throws SofaRpcException {
        log.info("sofaRequest:{}",sofaRequest);
        SofaResponse sofaResponse = filterInvoker.invoke(sofaRequest);
        log.info("sofaResponse:{}",sofaResponse);
        return sofaResponse;
    }
}
