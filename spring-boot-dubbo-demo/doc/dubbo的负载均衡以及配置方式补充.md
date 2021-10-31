# dubbo的负载均衡以及配置方式补充

### 前言

昨天我们分享了`dubbo`的后台管理控制台，简单介绍了它的基本用法，同时我也发现了自己在`dubbo`方面知识点的缺失，所以从今天开始，我们就开始学习`dubbo`的进阶知识点，然后逐步扫清`dubbo`的知识盲区。

今天我们要分享的内容主要有两个，一个是对之前配置内容的补充，一个就是`dubbo`负载均衡方面的基础知识。好了，让我们直接开始吧。

### 配置补充

首先，先看配置的补充。周五的时候，我们补充了一些关于`dubbo`配置的相关知识点，介绍了注解式配置`dubbo`，但是在今天进行`dubbo`负载均衡的时候，发现在注册多个服务提供者的时候，一直报端口被占用的错误：

![](https://gitee.com/sysker/picBed/raw/master/images/20210816124837.png)

搞了一早上都没找到问题在哪，所以早上出门前，`demo`没搞定，相关内容也一点没写。到公司后，我又抽时间继续找问题，几经折腾，问题也终于找到了，最后发现问题处了配置上，我之前的配置是这么写的：

```properties
# dubbo应用配置
# dubbo应用名称
application.dubbo.application.name=dubbo-server
# dubbo注册配置
# dubbo注册中心地址
application.dubbo.registry.address=zookeeper://127.0.0.1:2181
# dubbo注册中心类型
application.dubbo.registry.client=curator
#application.dubbo.registry.username=dubbo
#application.dubbo.registry.password=dubbo
application.dubbo.registry.port=20880
```

我一直以为这里的`application.dubbo.registry.port`就是服务的注册端口，所以改来改去，服务依然提示端口被占用。直到在某个机缘巧合的时刻，我打开了`DubboConfigConfiguration`这个配置类，我才发现自己少配置了：

![](https://gitee.com/sysker/picBed/raw/master/images/20210816125821.png)

在之前分享的内容中，我们只配置了`AppliationConfig`和`RegistryConfig`，而没有配置`ProtocolConfig`，这个配置类设定的是协议相关的信息，其中就包括我们这里需要指定的服务端口：

![](https://gitee.com/sysker/picBed/raw/master/images/20210816130036.png)

然后我们只需要加上相应的配置即可解决上面的问题。这里配置的方式有多种，这里我们介绍一种新的配置方式（你可以按照我们之前分享的那种方式将配置文件直接注入）：

首先在我们的配置类上加上`@EnableDubbo`注解，这个注解的作用就是启用`dubbo`的相关配置：

![](https://gitee.com/sysker/picBed/raw/master/images/20210816130633.png)

我们之前的配置就不需要了，直接注释或者删除就可以了。同时，这个注解经为我们集成了`@DobbuComponentScan`和`@EnableDubboConfig`注解，所以我们不需要再重复注解：

![](https://gitee.com/sysker/picBed/raw/master/images/20210816130548.png)

然后我们在`application.properties`加上对应的注解即可：

```properties
# dubbo应用配置
# dubbo应用名称
dubbo.application.name=dubbo-server
# dubbo注册配置
# dubbo注册中心地址
dubbo.registry.address=zookeeper://127.0.0.1:2181
# dubbo注册中心类型
dubbo.registry.client=curator
dubbo.registry.protocol.port=20881
dubbo.registry.port=20881
dubbo.protocol.name=dubbo
dubbo.metadata-report.address=zookeeper://127.0.0.1:2181
```

需要注意的是，这个里的配置前缀必须与`DubboConfigConfiguration`指定的配置保持一致，否则相关配置无法正常注入。

完成上面的配置，我们再次启动服务，发现我们服务的端口已经发生变化，同时修改注册端口，再启动一个服务也是`ok`的：

![](https://gitee.com/sysker/picBed/raw/master/images/20210816131117.png)

好了，到这里，配置相关的内容就完了，其他配置可以参考我们上面的方式进行修改，目前我们就先分析这么多。

### 负载均衡

关于`dubbo`的负载均衡，我们今天只说一些简单内容，因为负载均衡这块的内容也不是很复杂，如果你只是简单使用，只需要了解各种策略的大概原理效果即可。当然，如果你有自定义负载均衡扩展的需求，那你可能要深入去了解了，具体的可以参考官方文档：

```
https://dubbo.apache.org/zh/docs/advanced/loadbalance/
```

#### 负载均衡策略

`Dubbo`官方为我们提供了四种负载均衡策略，分别是**随机**策略、**轮询**策略、**最少活跃调用数**策略和**一致性 Hash**策略。如果我们不指定负载均衡策略，默认情况下为随机策略。

![](https://gitee.com/sysker/picBed/raw/master/images/20210816132418.png)

![](https://gitee.com/sysker/picBed/raw/master/images/20210816132443.png)

- **随机**，按权重设置随机概率。在一个截面上碰撞的概率高，但调用量越大分布越均匀，而且按概率使用权重后也比较均匀，有利于动态调整提供者权重。
- **轮询**，按公约后的权重设置轮询比率。存在慢的提供者累积请求的问题，比如：第二台机器很慢，但没挂，当请求调到第二台时就卡在那，久而久之，所有请求都卡在调到第二台上。
- **最少活跃调用数**，相同活跃数的随机，活跃数指调用前后计数差。使慢的提供者收到更少请求，因为越慢的提供者的调用前后计数差会越大。
- **一致性 Hash**，相同参数的请求总是发到同一提供者。当某一台提供者挂时，原本发往该提供者的请求，基于虚拟节点，平摊到其它提供者，不会引起剧烈变动。

负载均衡源码位于`org.apache.dubbo.rpc.cluster.loadbalance`包下，后面我们有时间再来研究相关实现原理，今天就暂时提一下：

![](https://gitee.com/sysker/picBed/raw/master/images/20210816133154.png)

#### 负载用法

负载均衡默认情况下也是启用的，只是策略默认是随机，为了演示方便，我们这里指定为轮询。

服务端负载我也指定的是轮询策略，但是好像没有啥效果，请求始终在同一个服务上。

服务端指定负载均衡策略后是有效果的，下面是客户端的代码：

```java
@RestController
@RequestMapping("/dubbo")
public class DemoController {
    @DubboReference(version = "1.0", interfaceName = "demoService", interfaceClass = DemoService.class, loadbalance = "roundrobin")
    private DemoService demoService;

    @RequestMapping("/test")
    public Object demo() {
        String hello = demoService.sayHello("world");
        System.out.println(hello);
        return hello;
    }
}
```

访问两次`/dubbo/test`接口后，我们看到，两个服务分别被访问一次：

![](https://gitee.com/sysker/picBed/raw/master/images/20210816160143.png)

说明负载均衡已经起作用了。

### 总结

今天我们分享了两个知识点，一个是`dubbo`的另一种配置方式的补充，另一个是`dubbo`的负载均衡。

在配置补充的相关内容中，我们演示了`dubbo`官方提供的配置方式，这种方式可以提供更丰富、更全面的配置，当然，最主要的是你要搞清楚各种配置方式之间的区别，搞清楚`dubbo`配置的本质，这样不论你用那种配置方式，都可以信手拈来。

 另外一块是关于`dubbo`负载均衡的，主要分为两种，一种是针对客户端的，一种是针对服务端的，客户端的我们演示过了，服务端的负载均衡好像没啥效果，可能是我的打开方式不对，后面有机会的话，我们再来研究。

 `dubbo`的负载均衡在使用方面还是比较简单，没有特别复杂的配置，也不需要引入第三方包，只需要在接口调用的时候指定负载均衡策略即可，当然底层实现也没有特别复杂，这些策略本质上就是为了获取服务调用地址，即确定具体调用哪个服务，如果目前的四种负载均衡策略无法满足你的需求，你也可以自定义自己的负载策略。

最后，附上我们示例的项目地址，最近几天都忘记了，感兴趣的小伙伴就可以去看下：

```

```

