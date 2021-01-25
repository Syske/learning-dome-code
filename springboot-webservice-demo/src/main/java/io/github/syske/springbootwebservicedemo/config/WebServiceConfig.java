package io.github.syske.springbootwebservicedemo.config;

import io.github.syske.springbootwebservicedemo.service.Hello2Service;
import io.github.syske.springbootwebservicedemo.service.HelloService;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

/**
 * @program: springboot-webservice-demo
 * @description: webservice配置类
 * @author: syske
 * @create: 2020-04-28 12:27
 */
@Configuration
public class WebServiceConfig {

    @Autowired
    private HelloService service;
    @Autowired
    private Hello2Service service2;

    /**
     * 注入servlet  bean name不能dispatcherServlet 否则会覆盖dispatcherServlet
     * @return
     */
    @Bean(name = "cxfServlet")
    public ServletRegistrationBean cxfServlet() {
        return new ServletRegistrationBean(new CXFServlet(),"/service/*");
    }


    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }

    /**
     * 注册WebServiceDemoService接口到webservice服务
     * @return
     */
    @Bean(name = "HelloService")
    public Endpoint sweptPayEndpoint() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), service);
        endpoint.publish("/service");
        return endpoint;
    }

    /**
     * 注册WebServiceDemoService接口到webservice服务
     * @return
     */
    @Bean(name = "Hello2Service")
    public Endpoint sweptPayEndpoint2() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), service2);
        endpoint.publish("/service2");
        return endpoint;
    }

}
