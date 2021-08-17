# spring-cloud应用网关之zuul

### 前言

应用网关这个组件，想必大家应该都不陌生，特别是在当下微服务盛行的互联网时代，应用网关有着非常重要的作用，也是微服务必不可少的组件之一。

通常我们将应用网关放在企业应用的最外层，作为企业应用的第一道大门，所有服务都经过应用网关来访问，这样做有很多好处，比如更安全，所有应用只对外暴漏应用网关的服务映射地址，隐藏服务实际地址、部署节点等信息，确保服务系统安全性；更易于扩展，通过应用网关，我们可以把集成很多其他功能，比如权限中心、用户中心、服务负载中心等服务，这样其他服务只需要接入应用网关即可使用相关公共服务提供的服务，而不用单独集成。好了，关于应用网关我们暂时就说这么多，下面我们看下`zuul`网关的简单应用。

### ZUUL

#### 创建项目

首先，我们要先创建一个`spring boot`项目。然后引入`zuul`和`eureka`的相关依赖。

##### 核心依赖

下面就是`zuul`的核心依赖，必须引入。

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
    <version>2.2.9.RELEASE</version>
</dependency>
```

引入`zuul`的依赖后，我发现`zuul`还依赖了`ribbon`、`feign`、`hystrix`等组件，说明`zuul`本身也为我们提供负载均衡的相关实现，关于这一点，后面我们会演示。

![](https://gitee.com/sysker/picBed/raw/master/20210807154031.png)

##### eureka依赖

引入`eureka`的客户端依赖是因为`zuul`应用网关的核心实现其实是基于`eureka`实现的，没有`eureka`的支持，`zuul`本身是无法发现服务具体地址的，关于这一点，我们后面也会演示。

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    <version>2.2.9.RELEASE</version>
</dependency>
```

##### 基本配置

我们只需要在项目主类加上`@EnableZuulProxy`即可启用`zuul`应用网关

```java
@SpringBootApplication
@EnableZuulProxy
public class SpirngCloudZuulDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpirngCloudZuulDemoApplication.class, args);
    }

}
```

同时，点开`@EnableZuulProxy`注解，我们会发现，其实它默认为我们集成并启用了熔断器，当然从最前面的依赖也可以看出这一点。

关于启用`Hystrix`断路器，从业务上也很好理解，应用网关作为对外提供服务的窗口，在前后端交互方面是绝对的挑大梁角色，如果因为某个服务长时间未响应，导致请求阻塞，最后导致网关宕机，这种情景是不敢想象的，网关一挂，意味着企业服务都凉凉了，所以集成断路器就很有必要了。

![](https://gitee.com/sysker/picBed/raw/master/20210806085128.png)

这里就是应用网关的配置文件了，配置内容也很简单，一个是指定端口，一个是设定`eureka`注册中心的地址。用`80`端口的好处，就是可以省掉端口，就很方便了

```properties
server.port=80
eureka.client.service-url.defaultZone=http://localhost:8999/eureka/
```



#### 访问测试

下面我们来进行一些简单的测试，首先启动我们的注册中心，为了方便测试，这里我们就启动一个注册中心，同时我们启动应用网关服务`spring-cloud-zuul`和产品服务`product-service`。

![](https://gitee.com/sysker/picBed/raw/master/20210806082539.png)

在产品服务中，我们有一个简单的`controller`接口，这个接口就是上次我们测试`feign`负载均衡组件的时候用的，这里就直接用了。

![](https://gitee.com/sysker/picBed/raw/master/20210806082615.png)

下面我们通过应用网关访问下我们的产品服务，地址如下：

```
http://localhost/product-service/feign
```

这里解释下我们上面的地址，`localhost`是应用网关的服务地址，`product-service`是我们要访问的服务`id`，这个`id`是我们`eureka`中注册的服务`id`，也就是我们服务中配置的`spring.application.name`的值，`/feign`就是我们接口的地址了。这里再啰嗦一下，通过`zuul`网关访问的服务，必须在`eureka`中心注册，否则无法发现服务。

下面就是我们访问结果：

![](https://gitee.com/sysker/picBed/raw/master/20210806082415.png)

和我们直接访问服务实际地址返回的结果也是一致的：

![](https://gitee.com/sysker/picBed/raw/master/20210807160745.png)

同时在后端控制台，我们会发现`zuul`的负载均衡默认是通过`ribbon`实现的：

![](https://gitee.com/sysker/picBed/raw/master/20210807162141.png)

#### 内容扩展

##### 服务配置

下面是`zuul`应用网关的一些特殊配置：

```properties
# 微服务映射规则
# 配置访问规则
zuul.routes.product-service.path=/p/**
# 配置服务地址
#zuul.routes.product-service.url=http://baidu.com/
zuul.routes.product-service.url=http://localhost:9001/
# 指定服务id
zuul.routes.product-service.service-id=product-service
```

上面这些配置是一些特殊的访问规则，下面简单解释下：

第一行配置是设定`product-service`的访问规则，也就是说只要我们的访问地址符合`/p/**`的匹配规则，也就是说我们可以通过`/p`来访问我们的服务，而不必再指定`serviceId`；

第二行配置设置的是`product-service`的服务地址；

第三行配置设定的是服务的`service-id`；

只是上面这种配置式的访问规则，并不会触发负载均衡，只是单纯的请求转发。关于这一点，各位小伙伴可以访问同一个服务试下，配置式的访问规则，服务访问的时候只会访问我们设定的`url`，而通过`serviceId`的方式访问，则会默认走负载均衡：

![](https://gitee.com/sysker/picBed/raw/master/20210807165827.png)

### 总结

好了，`zuul`应用网关的相关知识点暂时就先分享到这里，我们目前就先分享一些简单的应用，后期有机会的话，我们再进一步挖掘。

最近一段时间，分享知识节奏有点快，到今天差不多一周的时间，我们已经分享了`eureka`、`feign`、`fibbon`、`hystrix`、`zuul`等组件，虽然节奏快，但是内容方面我觉得应该算是尽可能详实了吧，当然很多内容的深度可能还不够，但是现阶段我们的目标只是了解和应用这些常用组件，至于各个组件的实现原理和高级用法，那是我的下阶段目标，所以暂时就先这样。