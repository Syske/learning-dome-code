# RPC框架之dubbo简单入门

## 前言

现阶段，`web`后端开发主流的接口协议类型主要有两种，一种就是我们传统的`rest`接口，另一种比较流行的就是`rpc`，今天我们就来简单说下`rpc`接口，同时我们会通过一个简单示例，来分享`dubbo`框架的基本用法。

`rpc`全称`Remote Procedure Call`，中文的意思是远程程序调用。简单来说，`rpc`就是一种基于`socket`的调用方式，一种有别于`rest`的调用协议。其核心技术就是动态代理，关于`rpc`动态代理的实现，我们前面其实有写过一个简易版的`rpc`接口，有兴趣的小伙伴可以去看下：



下面是`rpc`的调用原理，这里放上百度百科的一张图片，供大家参考：

![](https://gitee.com/sysker/picBed/raw/master/images/20210616082500.png)

好了，关于`rpc`的理论介绍，我们先说这么多，下面我们通过一个实例来分享下`dubbo`的简答用法。

## dubbo

我相信很多小伙伴经常在实际开发过程中有用到`rpc`，但是对于`rpc`的相关知识，很多小伙伴肯定没有系统学习过（当然也包括我），因为我们在实际工作中，大部分的时间都花在了业务实现方面，很少有机会能真正参与系统架构的搭建，所以很多时候这些知识就显得不那么重要了。

但是考虑到未来个人职业发展，同时也为了让我们在日常工作中更快地解决各类`rpc`的相关问题，掌握一些`rpc`的基础知识就尤为重要了，所以从今天开始，我们开始系统地探讨下`rpc`的相关知识，下面我们先从一个简单的`dubbo`实例开始。

关于`dubbo`我想大家应该都比较熟悉了，就算实际工作没有用到，在面试的过程中也一定听过。`dubbo`是`alibaba`开源的一款`rpc`服务框架，被各大公司广泛应用，现在也算是`java`开发必学的`web`开发框架之一，目前最新版本是`3.0`。想要了解更多信息，可以去官方网站看下：

```
https://dubbo.apache.org/zh/
```

![](https://gitee.com/sysker/picBed/raw/master/20210811082136.png)

#### 创建项目

首先我们要创建一个`maven`项目，然后分别创建三个模块：`common-facade`、`service-provider`和`service-consumer` 。其中`service-provider`和`service-consumer`是`spring-boot`项目，他们分别是服务提供者和服务消费者；`common-facade`是普通`maven`项目，它主要用来存放我们的`facade`接口，创建完成后，项目结构如下：

![](https://gitee.com/sysker/picBed/raw/master/images/20210811133944.png)

#### facade接口

项目创建完成后，我们先来编写`facade`接口。它就是一个简单的`interface`接口，不需要添加任何配置文件，也不需要引入第三方包，通常情况下我们会尽可能保证接口的简洁性。

它就相当于一个接口规范，服务提供者通过实现它的接口提供服务，服务消费者通过它消费服务，所以在服务提供者和消费者的项目中都要引用`common-facade`的包，我们通常还会把接口的出入参放在该包下。对于需要调用我们服务的项目，直接引入我们的`facade`接口包即可。

```java
package io.github.syske.common.facade;
/**
 * 示例服务
 *
 * @author syske
 * @version 1.0
 * @date 2021-08-11 8:34
 */
public interface DemoService {
    String sayHello(String name);
}
```

#### 服务提供者

服务提供者相对内容比较多，它不仅要引入`dubbo`的相关依赖，还需要引入`zookeeper`的相关依赖，同时还需要对服务注册做一些设置。

##### 项目依赖

首先我们看下服务提供者的依赖：

```xml
<!-- facade接口 -->
<dependency>
    <groupId>io.github.syske</groupId>
    <artifactId>common-facade</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
<!-- dubbo核心依赖 -->
<dependency>
    <groupId>org.apache.dubbo</groupId>
    <artifactId>dubbo</artifactId>
    <version>2.7.7</version>
</dependency>
<!-- zookeeper客户端依赖 -->
<dependency>
    <groupId>org.apache.curator</groupId>
    <artifactId>curator-recipes</artifactId>
    <version>3.3.0</version>
</dependency>
```

这里解释下最后一个依赖。`Curator`是`Netflix`公司开源的一套`Zookeeper`客户端框架，而`Recipes`是`Zookeeper`典型应用场景的实现。

这里我专门去查了一下`Netflix`公司，竟然就是大名鼎鼎的奈飞公司，该公司核心业务就是视频点播，如果你有留意之前我们分享的`spring-cloud`的相关内容的话，你会在发现`spring-cloud`的好多组件就是出自这家公司，比如`eureka`、`hystrix`、`ribbon`、`zuul`，从这一点上来说，这家公司还是很优秀的。

##### 项目配置

首先我们需要通过`@DubboComponentScan`指定`facade`服务提供者的包名，用于服务注册，缺少这个注解服务无法正常注册，这里可以不指定包名，默认情况下应该会取当前类的包名；

这里我们还需要指定`ApplicationConfig`，也就是服务提供者配置信息，如果缺少这个配置，启动的时候会报错：

![](https://gitee.com/sysker/picBed/raw/master/images/20210811175923.png)

另外还有服务注册配置`RegistryConfig`需要指定，这里主要指定的是`zk`的地址、`zk`客户端类型，`zk`客户端类型在`dubbo-2.1.1`及以后的版本只能选`curator`，在`dubbo-2.7.0`及以前的版本应该有两种，这也是我们为什么要引入`curator-recipes`客户端的原因。

![](https://gitee.com/sysker/picBed/raw/master/images/20210811181451.png)

![](https://gitee.com/sysker/picBed/raw/master/images/20210811181710.png)

如果你这里设置的是`zkclient`，启动的时候是会报错的：

![](https://gitee.com/sysker/picBed/raw/master/images/20210811181951.png)

下面是完整配置：

```java
@SpringBootApplication
@DubboComponentScan(value = "io.github.syske.demo.service.facade")
public class DemoProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoProviderApplication.class, args);
    }

    @Bean
    public ApplicationConfig applicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("dubbo-server");
        return applicationConfig;
    }

    @Bean
    public RegistryConfig registryConfig() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("zookeeper://127.0.0.1:2181");
        registryConfig.setClient("curator");
        return registryConfig;
    }

}
```

##### 编写服务实现

服务实现就比较简单了，只需要继承`facade`接口，然后实现对应方法即可，需要注意的是，我们需要在接口实现加上`@DubboService`和`@Service`注解，这两个注解一个是把接口服务注册成`rpc`接口，一个是把接口实例注册成`spring bean`。

方法内部，我还打印接口调用时的信息，方便我们后面查看。

```java
@Service
@DubboService
public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello(String name) {
        System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Hello " + name +
                ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
        return "Hello " + name + ", response from provider: " + RpcContext.getContext().getLocalAddress();
    }
}
```

其实这里的`@Service`和`@DubboService`就等同于下面的`xml`

```xml
<bean id="demoService" class="io.github.syske.demo.service.facade.DemoServiceImpl"/>
<dubbo:service interface="io.github.syske.common.facade.DemoService" ref="demoService"/>
```

##### 测试

完成以上工作，我们的服务提供者就配置完成了，这时候只需要先启动`zk`组件，然后再运行我们的服务提供者即可，正常情况下，服务启动成功后，会在`zk`中查到：

![](https://gitee.com/sysker/picBed/raw/master/images/20210811183752.png)

#### 服务消费者

完成服务提供者之后，服务消费者就相对简单了。

##### 项目依赖

项目依赖和服务提供者一样，没有任何区别

```xml
<dependency>
    <groupId>io.github.syske</groupId>
    <artifactId>common-facade</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>

<dependency>
    <groupId>org.apache.dubbo</groupId>
    <artifactId>dubbo</artifactId>
    <version>2.7.7</version>
</dependency>

<dependency>
    <groupId>org.apache.curator</groupId>
    <artifactId>curator-recipes</artifactId>
    <version>3.3.0</version>
</dependency>
```

##### 项目配置

项目配置也是一致的

```java
@SpringBootApplication
@DubboComponentScan
public class DemoConsumerApplication {


    public static void main(String[] args) {
        SpringApplication.run(DemoConsumerApplication.class, args);
    }

    @Bean
    public ApplicationConfig applicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("dubbo-server");
        return applicationConfig;
    }

    @Bean
    public RegistryConfig registryConfig() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("zookeeper://127.0.0.1:2181");
        registryConfig.setClient("curator");
        return registryConfig;
    }
}
```

##### 服务消费者

消费这里也比较简单，只需要通过`@DubboReference`引入`facade`接口即可，然后在需要的地方调用即可

```java
@RestController
@RequestMapping("/dubbo")
public class DemoController {
    @DubboReference
    private DemoService demoService;

    @RequestMapping("/test")
    public Object demo() {
        String hello = demoService.sayHello("world");
        System.out.println(hello);
        return hello;
    }
}
```

消费者`@RequestMapping`注解等同于下面的配置

```xml
<dubbo:reference id="demoService" check="true" interface="io.github.syske.common.facade.DemoService.DemoService"/>
```

##### 测试

完成相关配置后，启动服务消费者，然后去`zk`看下注册信息：

![](https://gitee.com/sysker/picBed/raw/master/images/20210811193738.png)

#### 测试

下面我们直接访问消费者的接口`/dubbo/test`看下：

![](https://gitee.com/sysker/picBed/raw/master/images/20210811193951.png)

消费者控制台：

![](https://gitee.com/sysker/picBed/raw/master/images/20210811194020.png)

服务提供者控制台：

![](https://gitee.com/sysker/picBed/raw/master/images/20210811194058.png)

可以看到服务已经访问成功了，数据也成功返回了。

#### 踩坑

如果你在本地启动过程中，有如下报错，请检查本地`zookeeper`依赖版本，根据提示信息，`zookeeper`的版本必须大于`3.3.3`。默认情况下，`curator-recipes`是会为我们引入`zk`的，所以我们一般不需要引用。

![](https://gitee.com/sysker/picBed/raw/master/images/20210811140507.png)

![](https://gitee.com/sysker/picBed/raw/master/images/20210811174940.png)



### 总结

今天我们主要分享了`spring-boot`整合`dubbo`的整个过程，过程中踩了好多坑，但是还好问题都解决了，由于过程断断续续搞了一天，所以好多报错都没来得及记录，不过你如果参照我们今天的整合过程的话，应该是没有问题的。好了，今天内容就到这里吧!