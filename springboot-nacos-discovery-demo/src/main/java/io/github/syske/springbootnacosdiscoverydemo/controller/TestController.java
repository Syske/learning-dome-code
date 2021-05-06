package io.github.syske.springbootnacosdiscoverydemo.controller;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import io.github.syske.springbootnacosdiscoverydemo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @program: springboot-nacos-discovery-demo
 * @description: nacos注册中心测试Controller
 * @author: syske
 * @create: 2021-03-07 21:35
 */
@RestController
@RequestMapping("test")
public class TestController {
    @NacosInjected
    private NamingService namingService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TestService testService;

    /**
     * 获取已注册服务的注册信息
     *
     * @param serviceName
     * @return
     * @throws NacosException
     */
    @RequestMapping(value = "/get", method = GET)
    @ResponseBody
    public List<Instance> get(@RequestParam String serviceName) throws NacosException {
        return namingService.getAllInstances(serviceName);
    }

    /**
     * 注册服务
     *
     * @param serviceName
     * @param ip
     * @param port
     * @return
     * @throws NacosException
     */
    @RequestMapping(value = "/register", method = GET)
    @ResponseBody
    public String register(@RequestParam String serviceName, @RequestParam String ip,
                           @RequestParam int port) throws NacosException {
        namingService.registerInstance(serviceName, ip, port);
        return "服务注册成功";
    }

    /**
     * 发现并调用服务
     *
     * @param serviceName
     * @return
     * @throws NacosException
     */
    @RequestMapping(value = "/getService", method = GET)
    @ResponseBody
    public String getService(@RequestParam String serviceName) throws NacosException {
        Instance instance = namingService.getAllInstances(serviceName).get(0);
        String serviceUrl = "http://" + instance.getIp() + ":" + instance.getPort()
                + "/test/hello" + "?name=test";
        System.out.println(serviceUrl);
        return restTemplate.getForObject(serviceUrl, String.class);
    }


    /**
     * 被注册的服务
     *
     * @param name
     * @return
     */
    @RequestMapping(value = "/hello", method = GET)
    @ResponseBody
    public String sayHello(@RequestParam String name) {
        return testService.sayHello(name);
    }
}
