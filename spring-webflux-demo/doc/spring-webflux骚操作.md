# spring-webflux骚操作

### 前言

今天我们来看下`webflux`比较奇特的操作。当然这些操作，在我们以前的`web`应用中很难实现的，至少实现起来并不容易。

### webflux

下面是一个`webflux`流式编程的典型样例，简单来说就是，前端请求一次，收到请求后会源源不断地向前端传输数据。

这感觉就像我们的水管一样，前端发送一个开启传输的指令，然后后端就开始像放水一样像前端传输数据了。

#### 接口配置

下面是接口的实现，这里和我们前面分享的接口有所不同，这里响应数据传输的容器用的是`Flux`，之前都用的是`Mono`。关于两者的区别，根据我查到的资料，`Mono`一般用于返回`0`或`1`个元素的异步序列`Publisher`，也就和我们传统的`MVC`很像；而`Flux`时返回`0`或者`N`个元素组成的`Publisher`（`Publisher`的作用类似于消息分发中心这样的角色，核心方法是`subscribe`）

```java
 public Mono<ServerResponse> sendTimePerSec(ServerRequest serverRequest) {
        return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(  // 1
                Flux.interval(Duration.ofSeconds(1)).   // 2
                        map(l -> new SimpleDateFormat("HH:mm:ss").format(new Date())),
                String.class);
    }
```

这个接口的作用是，间隔一秒向前端发送当前时间，时间格式`HH:mm:ss`。

这里需要注意的是，这里的请求头设置的是`TEXT_EVENT_STREAM`，这种响应头和之前的区别后面我们会演示，这里大家留意下就行。

`interval`是一个延迟回调方法，当达到指定的延迟时间`period`，方法返回值是循环的次数，从`0`开始。下面是官方文档给出的`interval`方法的示意图，还是比较形象的

![](https://gitee.com/sysker/picBed/raw/master/images/20210730141835.png)

`map`方法应该不需要过多说明吧，用过`lambda`表达式的小伙伴，应该很熟悉，在这里它的作用也是做类型转换，这里的写法是`lambda`的写法，完整写法是这样的：

```java
.map(new Function<Long, String>() {
                    @Override
                    public String apply(Long l) {
                        System.out.println(l);
                        return new SimpleDateFormat("HH:mm:ss").format(new Date());
                    }
                })
```

#### 路由配置

这里是设置我们接口的路由信息，包括请求方式、地址、处理器等。

```java
@Bean
    public RouterFunction<ServerResponse> routSayHi(HiHandler handler) {
        return RouterFunctions
                .route(RequestPredicates.GET("webflux/hi")
                        .and(RequestPredicates.accept(MediaType.ALL)), handler::sayHi)
                .andRoute(RequestPredicates.GET("/webflux/test")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::sendTimePerSec);
    }
```

#### 测试运行

访问我们的接口，你会发现，虽然前端之访问了一次，数据一直在持续输出，当然如果你点击浏览器上面的`X`，就相当于本次请求结束，数据也就不再传输了。

![](https://gitee.com/sysker/picBed/raw/master/20210730083452.png)

而且后端也在持续输出，就像水管一样，水一直在流动。

![](https://gitee.com/sysker/picBed/raw/master/images/20210730175922.png)

另外根据我们运行结果来看，我们发现`Flux`类型的响应数据和之前的响应类型（`Mono`）相比是不一样的，在`Flux`的响应数据中，我们会发现接口的响应头变成了`text/event-stream;charset=UTF-8`，也就是我们前面执行的响应头，而且多了一个请求头`transfer-encoding: chunked`，这个请求头表明我们的响应数据长度是不确定的，据说这种方式占用的资源更少，可以让数据一块一块输出。

![](https://gitee.com/sysker/picBed/raw/master/images/20210730182103.png)



![](https://gitee.com/sysker/picBed/raw/master/images/20210730181316.png)

关于两者区别，我暂时还不清楚，但是如果把`Mono`的响应体改成`TEXT_EVENT_STREAM`，返回结果依然正常，但是如果把`Flux`的响应类型改成`APPLICATION_JSON`，虽然页面依然会持续有数据输出，但是看起来比刚才奇怪了：

![](https://gitee.com/sysker/picBed/raw/master/images/20210730183335.png)

好了， 关于`webflux`的流式编程就先演示到这里，后面我们看情况继续深入。

### 总结

经过这几天的摸索和学习，我慢慢对`webflux`有了更深入的了解和恶认识，但是同时也发现`webflux`的相关只是真的是太多了，而且是越学习感觉不知道的东西越多（知识的悖论，知识的诅咒），所以也只能说自己以前想的太天真了，竟然还想通过三五天的时间就学会`webflux`。

另外，我也发现目前市面上关于`webflux`的相关知识、书籍和博客都比较少，而且大部分都不够系统，所以关于`webflux`的学习，我已经做好了持久战的准备，以后应该不会每天更新，这样确实比较容易上头，另一方面，如果积累的知识点不够的话，很容易言之无物，所以综合起来看，拉长战线会是一个比较合理的选择。

最后，给各位i小伙伴一个建议，在学习的时候，一定要定期去复习，不然之前学习的知识点比较容易忘记。这两天我在用线程池的时候，还专门去犯了之前的博客，因为好多东西又忘了

