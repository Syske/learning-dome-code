# spring-cloud服务之间的调用

### 前言

昨天，我们通过一个实例演示了，`spring-cloud`服务注册组件——`Eureka`的基本配置和简单用法，但是服务注册就是为了方便后期的发现和调用，所以今天我们趁热打铁，分享下`spring-cloud`服务之间的调用。

### 服务间的调用

关于`spring-cloud`的服务调用，我们首先需要了解它的两个核心组件`Ribbon`和`Feign`。

我们都知道，`spring boot`的接口都是基于`REST`实现的，但是在实际线上运行的时候，考虑到用户规模、服务可用性等方面的因素，我们一般很少是单节点运行的，通常都是以集群模式部署的，但是在集群部署中，又有一个核心的问题必须解决——负载均衡。关于负载均衡，各位小伙伴应该不陌生，最常用的组件之一`nginx`其中一个很核心的用途就是做负载均衡，但是`nginx`在实际做负载均衡的时候，确实不够方便，需要手动配置服务地址，如果服务地址发生变化，相关配置也需要修改，所以不够灵活。

当然`spring cloud`作为一款微服务综合框架，它自然也提供了自己的一套负载均衡解决方案，所以接下来我们就来看下`spring cloud`的负载均衡组件——`Ribbon`。

#### Ribbon

`Ribbon`中文的意思是丝带、带状物，正如它的含义，它就是连接调用方和服务之间的纽带。

##### 依赖

我们先通过一个简单实例，来演示下，然后在示例的过程中来解释，首先是它的核心依赖：

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
    <version>2.2.9.RELEASE</version>
</dependency>
```

##### 配置

这个组件你需要添加到服务**调用方**的依赖中。同时，还需要增加它的配置：

```java
@Configuration
public class RibbonConfig {

    // 多节点负载
    @LoadBalanced
    @Bean(name = "restTemplate")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
```

`@LoadBalanced`注解的作用是启用多节点负载，这样后期我们在调用的时候，`RestTemplate`客户端其实就是通过负载均衡的方式在调用服务提供者。

##### 服务调用方

然后，在服务调用方，我们通过`RestTemplate`去调用我们的服务提供者：

```java
@Autowired
private RestTemplate restTemplate;

@GetMapping("/ribbon")
public Object queryUserByProductId() {
    List<JSONObject> jsonObjectList = Lists.newArrayList();
    for (int i = 0; i < 10; i++) {
        JSONObject forObject = restTemplate.getForObject("http://user-center/user/" + (i + 1), JSONObject.class);
        jsonObjectList.add(forObject);
    }
    return jsonObjectList;
}
```

这里我们通过前面配置的`restTemplate`来调用我们的用户服务，接口的地址就是我们`eureka`注册中心显示的地址：

![](https://gitee.com/sysker/picBed/raw/master/images/20210802130731.png)

这里的地址不区分大小写，都可以正常访问。调用十次，主要是为了测试负载均衡的效果。

##### 服务提供者

首先我们看下服务提供者配置：

```properties
server.port=8776
eureka.client.service-url.defaultZone=http://localhost:8999/eureka, http://localhost:9000/eureka
```

第一个配置是指定服务的端口，如果在本地启动的话，需要每启动一次改一个端口，否则会提示端口冲突，如果你用的是`IDEA`的话，要先运行多应用启动：
![](https://gitee.com/sysker/picBed/raw/master/images/20210802132933.png)

第二个配置就是设定我们的注册中心，我们有两个注册中心，所以指定了两个地址。

服务提供者就是一个简单的`controller`，在`controller`内部，我们通过`DiscoveryClient`打印出被调用者的信息，方便我们查看。

```java
@RestController
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/user/{id}")
    public JSONObject getUserById(@PathVariable(name = "id") Long id) {
        List<ServiceInstance> instances = discoveryClient.getInstances("user-center");
        logger.info("instances = {}",  instances);
        JSONObject user = new JSONObject();
        user.put("id", id);
        user.put("name", "syske");
        return user;
    }
}
```

这里需要注意的是，我们导入的`DiscoveryClient`是`org.springframework.cloud.client.discovery`包下的，如果不是同这个类，启动的时候会报错：

```
Consider defining a bean of type 'com.netflix.discovery.DiscoveryClient' in your configuration.
```

##### 测试

我们分别启动服务调用发和被调用方，这里我启动了`5`个`user-center`，同时`eureka`服务也启动了两个，这个两个注册中心互相注册监控，在实际应用中也可以确保服务稳定性。`5`个`user-center`有`2`个注册在`8999`的注册中心上，有`3`个注册在`9000`的注册中心上：

![](https://gitee.com/sysker/picBed/raw/master/20210802085532.png)

![](https://gitee.com/sysker/picBed/raw/master/20210802085621.png)

然后，我们访问`product`的`ribbon`接口：

```
http://localhost:8881/ribbon
```

浏览器返回结果如下：

![](https://gitee.com/sysker/picBed/raw/master/images/20210802133217.png)

同时，在`user-center`端口为`8771`和`8775`的控制台，会看到如下信息：

![](https://gitee.com/sysker/picBed/raw/master/20210802085436.png)

![](https://gitee.com/sysker/picBed/raw/master/20210802085349.png)

为什么只有`8771`和`8775`收到了请求，因为`8771`和`8775`都注册到了`8999`的注册中心，而且我们的`product-service`也注册在该服务中心，所以就只调用了`8771`和`8775`这两个服务：

![](https://gitee.com/sysker/picBed/raw/master/20210802085532.png)

根据运行结果，我们还发现在`10`次请求中，`8771`和`8775`各处理五次，这里面还有一个潜藏的知识点：`Ribbon`默认的负载策略是轮询策略，这样可以确保同一个注册中心下的所有服务节点接收到同样的请求频次。

如果你把`user-center`（`5`个服务）、`product-serive`都注册在同一个注册中心，那么你会发现每个服务都会被调用`2`次。

### 总结

总体来说，`Ribbon`对用户来说感知确实不够强，而且经过我的测试，我发现就算拿掉`ribbon`的依赖，依然可以正常负载均衡，这是因为`eureka-client`的依赖，已经添加过`ribbon`的依赖了：

![](https://gitee.com/sysker/picBed/raw/master/images/20210802141228.png)

好了，今天的`Ribbon`分享就先到这里吧，我们明天分享基于`Feign`的声明式调用。