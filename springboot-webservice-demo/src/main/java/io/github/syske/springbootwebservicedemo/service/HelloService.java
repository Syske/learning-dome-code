package io.github.syske.springbootwebservicedemo.service;

import org.springframework.stereotype.Component;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * @program: springboot-webservice-demo
 * @description: webservice test interface
 * @author: syske
 * @create: 2020-04-28 09:30
 */
@WebService(targetNamespace = "http://service.ws.sample/", name = "HelloService")
public interface HelloService {

    @WebResult(name = "hello", targetNamespace = "")
    @RequestWrapper(localName = "name",
            targetNamespace = "http://service.ws.sample/",
            className = "SayHello2Param")
    @WebMethod(action = "urn:SayHello")
    @ResponseWrapper(localName = "hello",
            targetNamespace = "http://service.ws.sample/",
            className = "SayHello2Return")
    String sayHello(@WebParam(name = "myname", targetNamespace = "") String myname);
}
