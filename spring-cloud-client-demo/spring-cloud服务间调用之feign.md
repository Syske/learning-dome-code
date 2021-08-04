# spring-cloud服务间调用 - 下 （feign）

### 前言

昨天我们分享了`spring-cloud`基于`ribbon`的服务调用，通过一个简单实例演示了`ribbon`负载均衡调用的基本方式，同时我们也提到了一些需要注意的点，从总体内容上来说，代码量还是比较少的，而且过程也不算复杂，不过按照我最开始的想法，是计划把`feign`和它一起分享的，后来考虑到时间和篇幅的问题，就把`feign`放在今天来讲，所以今天我们就着重来分享下`spring-cloud`的另一种调用方式——声明式调用（`feign`）。

### Feign

#### 依赖

首先我们要引入`eureka`和`feign`的`pom`依赖，这里需要注意的是，`feign`的版本必须要与`spring-cloud`的版本保持一致，否则启动会报错，具体错误可以看后面踩坑部分的内容：

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    <version>2.2.9.RELEASE</version>
</dependency>

<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
    <version>2.2.9.RELEASE</version>
</dependency>
```

#### 调用方配置

引入`pom`依赖后，我们还需要服务对调用进行配置，配置的方式很简单，只需要在调用方核心类加上`@EnableFeignClients`注解

```java
@SpringBootApplication
@EnableFeignClients(basePackages = "io.github.syske.productservice")
public class ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }

}
```

这个注解的作用就是启用`FeignClients`调用，这里的包可以指定也可以不指定，不指定的话我猜测它应该会取`ProductServiceApplication`当前的包路径进行扫描。启用这个配置，`Feign`会扫描我们指定包下面声明的`Service`接口，然后在我们需要用到`service`的地方直接`@Autowired`即可（不知道是不是动态代理的方式）

这里的`service`接口，其实就是我们要调用的目标服务的接口服务，类似于`rpc`的`facade`，当然也不完全一样，只能说类似。

#### 定义接口服务

这里的接口服务就是我们前面说的`Service`接口，就是为了给后面调用准备的。

```java
@FeignClient("user-service")
public interface UserService {

    @GetMapping("/user/{id}")
    public JSONObject getUser(@PathVariable("id") Long id);
}
```

`@FeignClient("user-service")`指定的是我们要调用的服务，`user-service`是`eureka`注册的服务`id`，也就是我们`spring.application.name`配置的值，如果这个值配置的不争取，也是会报错的，具体看踩坑部分的内容。

`@GetMapping("/user/{id}")`指定的是接口的地址，这里的接口地址必须与被调用方保持一致，否则调不通，下面是我的被调用方的方法实现：

![](https://gitee.com/sysker/picBed/raw/master/images/20210803132304.png)

还是昨天的代码，啥都没变。

对比接口和被调用方实现，我们可以看出了，接口的方法名不必和方法一致，但是参数列表必须保持一致，否则也是会报错的：

![](https://gitee.com/sysker/picBed/raw/master/images/20210803132645.png)

#### 调用方实现

调用方实现就很简单了，就是一个简单的`controller`，只是这里需要通过`@Autowired`注入我们之前定义的声明式调用接口，然后直接调用即可。这种方式在我看来，就是很像`rpc`的调用

```java
@RestController
public class ProductController {

    @Autowired
    private UserService userService;

    @GetMapping("/feign")
    public Object getUserPo() {
        List<JSONObject> userList = Lists.newArrayList();
        for (long i = 0; i < 10; i++) {
            userList.add(userService.getUser((i + 1)));
        }
        return userList;
    }
}
```

#### 测试

下面我们启动`5`个服务提供者，启动一个服务消费者。为了方便测试，我们就直接注册到同一个`Eureka`注册中心:

![](https://gitee.com/sysker/picBed/raw/master/images/20210803133441.png)

然后访问`localhost:9002/feign`，结果正常返回：

![](https://gitee.com/sysker/picBed/raw/master/images/20210803133637.png)

同时每个服务都被调用两次，说明我们的负载效果也达到了：

![](https://gitee.com/sysker/picBed/raw/master/images/20210803133819.png)

好了，测试就到这里，下面我们看下今天的踩坑记录。

#### 踩坑

下面是今天实践过程中踩的一些坑，不过也正是这些坑，才让我对`feign`有了更深刻的认知。

##### 版本不一致导致启动报错

![](https://gitee.com/sysker/picBed/raw/master/20210803082224.png)

这个错误是由于版本问题导致，下面是我之前`pom`依赖的版本，可以看出`spring-cloud`和`feign`的版本是不一致的

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    <version>2.2.9.RELEASE</version>
</dependency>

<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
    <version>3.0.3</version>
</dependency>
```

解决方式也很简单，只需要把`openfeign`的版本改成`2.2.9.RELEASE`即可，即`cloud`组件的版本必须与`spring-cloud`的版本保持一致

##### 依赖不正确导致服务注册后关闭

后台虽然没有报错，但是服务未成功启动

![](https://gitee.com/sysker/picBed/raw/master/20210803082522.png)

查询了一些资料，发现这个问题也是由于依赖不正确导致的，如果你的`spring-boot`依赖是:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
</dependency>
```

那么你需要把`starter`改成`starter-web`，然后再次启动即可。

![](https://gitee.com/sysker/picBed/raw/master/20210803082815.png)

然后再看下我们的注册中心`Eureka`：

![](https://gitee.com/sysker/picBed/raw/master/20210803082856.png)

##### 服务Id不正确导致调用服务时报错

这个错误是调用的时候报的，查了一些资料发现是我的服务`id`写错了，我注册的服务是`user-service`，但是我写的是`user`，所报错了，下面是错误截图：

![](https://gitee.com/sysker/picBed/raw/master/20210803083600.png)

上面这个报错有两个原因，一个可能是服务名写错了；另一个就是你可能把`ribbon.eureka.enabled`设置成`false`，这个配置默认是`true`。不过通过这个错误，我发现`feign`应该是基于`ribbon`实现的，或者说它是基于`ribbon`实现的，关于这一点，我们后期可以研究下源码。

![](https://gitee.com/sysker/picBed/raw/master/20210803084522.png)

### 总结

今天的内容虽然有点长，但是也算是把`feign`讲的比较透彻，至少是在使用方面，我觉得应该没有太大问题，所以也没什么可总结的。

最后，我想说两句闲话，最近这几天内容更新都比较晚，一个主要原因就是早上时间不太够；有时候洗漱完就七点半了，然后写完`demo`基本的就八点多，留给内容梳理的时间只有差不多半小时，然后剩余的内容大都是中午午休时间完成的（差不多一个小时），不过对我而言，只要`demo`完成了，内容梳理起来还是比较容易的。

另外一个影响原因就是天气太热了，感觉有点不在状态，早上也不出活。最近这两天天气特别热，特别是昨天，昨天下班回家就停电了，然后吃完饭就一直在楼下溜达，一直到十一点多都没来电，后来实在没办法就回家睡了（没有空调、没有风扇，热死），虽然后来电了，但是没一会就又断了，总之就是断断续续的，也不知道昨天晚上咋睡过了来的😂。各位小伙伴一定要注意防暑，有机会多存点冷气，毕竟夏天还是很容易停电的
