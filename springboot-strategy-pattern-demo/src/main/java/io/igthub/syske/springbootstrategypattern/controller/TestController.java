package io.igthub.syske.springbootstrategypattern.controller;

import io.igthub.syske.springbootstrategypattern.entity.RequestHeader;
import io.igthub.syske.springbootstrategypattern.entity.WrapperRequest;
import io.igthub.syske.springbootstrategypattern.service.Myservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private Myservice myservice;

    @RequestMapping("/test")
    public String test() {
        WrapperRequest<String> stringWrapperRequest = new WrapperRequest<>();
        RequestHeader requestHeader = new RequestHeader();
        requestHeader.setBusinessCode("01001");
        stringWrapperRequest.setHeader(requestHeader);
        myservice.handler(stringWrapperRequest);
        return "success";
    }

    @RequestMapping("/test2")
    public String test2() {
        WrapperRequest<String> stringWrapperRequest = new WrapperRequest<>();
        RequestHeader requestHeader = new RequestHeader();
        requestHeader.setBusinessCode("01002");
        stringWrapperRequest.setHeader(requestHeader);
        myservice.handler(stringWrapperRequest);
        return "success2";
    }

    @RequestMapping("/test3")
    public String test3() {
        WrapperRequest<String> stringWrapperRequest = new WrapperRequest<>();
        RequestHeader requestHeader = new RequestHeader();
        requestHeader.setBusinessCode("01003");
        stringWrapperRequest.setHeader(requestHeader);
        myservice.handler(stringWrapperRequest);
        return "success3";
    }

}
