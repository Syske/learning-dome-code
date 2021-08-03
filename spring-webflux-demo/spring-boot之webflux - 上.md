# spring-boot之webflux - 上

### 前言

从今天开始，我们开始学习`spring-boot`的另外一套`web`解决方案——`webflux`。

`webFlux`是`spring`在`5.0`以后的版本中引入的，我们都知道，`spring`的`mvc`框架是基于`servlet`实现的，本质上他用的是传统的`I/O`模型，相比于传统的`MVC`，`WebFlux`则是基于`NIO`（新`IO`，也叫非阻塞`IO`），所以在`IO`性能上会比传统的`MVC`性能要好一些。

下面是官方网站给出的对比：

![](https://gitee.com/sysker/picBed/raw/master/images/20210728130218.png)

下面是`MVC`和`webFlux`的架构图对比:

![](https://gitee.com/sysker/picBed/raw/master/images/20210728133517.png) 

当然，现在我们的说法只能算是一种推理，因为我现在对`webflux`也知之甚少，不过根据我目前搜集到的资料来看，大家似乎并不认为`webflux`未来会成为一种趋势，大家诟病的缺点主要有以下几个：

- 写法结构上和`lambda`表达式很像（响应式编程），所以在日常开发运维中，不利于问题排查，不便于调试（用过`lambda`s表达式的小伙伴也应该有此体会）
- 写法上过于繁琐，相比于传统的`MVC`，过于臃肿，这一点我们等下可以对比看下
- 打印日志、堆栈信息不方便，不利于后期运维

这些就是我目前所了解到的缺点，更多的可能还需要我们自己实际踩坑才能体会，下面我们就来通过一个简单的`demo`看下`webflux`的一些基础知识。

### Webflux

#### 创建项目

首先我们创建一个`spring-boot`项目，引入`webflux`的依赖:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webflux</artifactId>
</dependency>
```

#### 编写Handler组件

这里的`handler`就类似于我们之前`mvc`中的`controller`，也就是对外提供的接口。

我们可以在方法内部指定返回结果、响应类型、响应体等数据，这种写法和我们前几天写的`security`的配置很像，也和`Netty`代码中的写法很像，不过这也不奇怪，因为`webFlux`的底层就是基于`Netty`实现的。

```
@Component
public class HiHandler {
    public Mono<ServerResponse> sayHi(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue("Hi , this is SpringWebFlux"));
    }
}
```

#### 编写路由配置

路由配置和`Handler`是对应的，一个`Handdler`对应一个路由配置

```java
@Configuration
public class RouteConfig {
    @Bean
    public RouterFunction<ServerResponse> routSayHi(HiHandler handler) {
        return RouterFunctions
                .route(RequestPredicates.GET("webflux/hi")
                        .and(RequestPredicates.accept(MediaType.ALL)), handler::sayHi);
    }
}
```

#### 测试

写完以上代码后，我们启动项目，然后访问如下地址：

```
http://localhost:8999/webflux/hi
```

服务端口如果你没有修改的话，默认应该是`8080`。响应结果如下：

![](https://gitee.com/sysker/picBed/raw/master/images/20210728131526.png)

到这里你的第一个`webflux`接口就写好了。

#### 传统MVC写法

对于上面这个接口，如果我们用传统的`MVC`写法的话，只需要一个`controller`接口即可：

```
@RestController
@RequestMapping("/webflux")
public class WebFluxController {

    @RequestMapping("/hi")
    public Object sayHi(String name) {
        return "hi, " + name;
    }
}
```

启动访问：

```
http://localhost:8999/webflux/hi?name=syske
```

![](https://gitee.com/sysker/picBed/raw/master/images/20210728131952.png)

需要注意的是，如果`controller`和`webFlux`接口同时存在的话，`webFlux`会覆盖`controller`，也就是说`webFlux`的优先级要高于`controller`。

#### MVC与WebFlux对比

先抛开性能差异，我们但从代码层面看，`webflux`要比`MVC`写更多代码，而且在写法上要复杂。所以，但从现在的情况来看，我觉得`webflux`是没有优势的，更多差异只能等我们后面深入了解之后才能清楚。

### 总结

今天我就只展示`webFlux`的简单写法，让各位小伙伴对`webflux`有个最基本的认识，认识到`webflux`和传统`MVC`的基本区别，当然这些都数据特别特别基础的知识。从明天开始我们就要开始稍微深入的探讨，然后逐步深入了解`webflux`的一些原理。

因为我现在也和各位小伙伴一样，也是在一边学一边分享，摸着石头过河，如果那里有说的不正确的地方，还请各位小伙伴予以指正。好了，今天就先到这里吧！

最后，附上以上实例源代码：

```
https://github.com/Syske/learning-dome-code/tree/dev/spring-webflux-demo
```

