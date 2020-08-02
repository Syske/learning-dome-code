package io.github.syske.springbootwebservicedemo.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * @program: springboot-webservice-demo
 * @description: 第二个webservice
 * @author: syske
 * @create: 2020-04-28 14:42
 */
@WebService(targetNamespace = "http://service.ws.sample/", name = "Hello2Service")
public interface Hello2Service {
    @WebResult(name = "hello2", targetNamespace = "")
    @RequestWrapper(localName = "sayHello2Param",
            targetNamespace = "http://service.ws.sample/",
            className = "SayHello2Param")
    @WebMethod(action = "urn:SayHello2")
    @ResponseWrapper(localName = "sayHello2Return",
            targetNamespace = "http://service.ws.sample/",
            className = "SayHello2Return")
    String sayHello2(@WebParam(name = "myname", targetNamespace = "") String myname);
}
