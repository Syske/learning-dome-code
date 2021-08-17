# spring-boot之webflux简单入门 - 中

### 前言

昨天我们通过一个简单实例，演示了`webflux`与`WebMVC`的简单区别，同时也展示了`webflux`的基本工作流程，今天我来继续学习`webflux`的相关知识。

今天我们学习的重点是`webflux`的客户端，我们想通过`webflux`客户端来了解`webflux`的相关基础知识，包括接口请求方式、响应类别、传参等

### webflux客户端

#### 简单示例

`webflux`的客户端用起来很方便，也很简单，我们先看这样一段代码：

```java
 WebClient client = WebClient.create("http://localhost:8999");
        Mono<String> stringMono = client.get()
                .uri("/webflux/hi")
                .attribute("name", "syske")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class);
        String block = stringMono.block();
        System.out.println("返回结果：" + block);
```

第一行代码就是实例化一个`weClient`对象，需要注意的是，这里实例化的时候，也可以不指定服务器地址，如果不指定服务器地址的话，就需要我们在`uri`方法中指定完整的接口地址：

```java
WebClient client = WebClient.create();
        Mono<String> stringMono = client.get()
                .uri("http://localhost:8999/webflux/hi")
                ···
```

如果不指定服务器地址，且`uri`填写相对接口地址，则会报如下错误信息：
![](https://gitee.com/sysker/picBed/raw/master/images/20210729132108.png)

#### 示例讲解

下面我们就来详细解释下上面`webfluxClient`的代码。

##### 请求类型

`client.get()`表示创建一个`get`请求对象（本质上是一个`RequestHeadersUriSpec`对象），他还支持`post`、`put`、`delete`和`options`，也就是我们`restful`的所有协议。

![](https://gitee.com/sysker/picBed/raw/master/images/20210729132522.png)

对于不同的请求类型，我们配置的请求参数是不一样的，但是`uri`方法都是通用的。

##### `get`客户端配置

当然，还有很多配置项是不一样的，在上面的示例中，我们是`get`请求，我们可以配置如下参数：

- 请求地址中的参数：也就是`url`中的参数，可以通过`attributes`方法设置

```java
Mono<String> stringMono = client.get()
                .uri("/webflux/hi")
                .attribute("name", "syske")
                ···
```

- `cookie`信息

```java
Mono<String> stringMono = client.get()
                .uri("/webflux/hi")
                .cookie("syske", "yyds")
                ···
```

- 响应头的字符编码

```java
Mono<String> stringMono = client.get()
                .uri("/webflux/hi")
                .acceptCharset(Charset.defaultCharset())
                ···
```

- 设置响应类型

也就是我们常用的媒体类型，包括`application/json`、`application/pdf`、`text/html`、`image/jpeg`等待

```java
Mono<String> stringMono = client.get()
                .uri("/webflux/hi")
				.accept(MediaType.APPLICATION_JSON)
				···
```

- 获取响应设置对象

```java
Mono<String> stringMono = client.get()
                .uri("/webflux/hi")
				.retrieve()
				···
```

- 配置响应对象

这里配置的类型要与服务器端对应，否则会在类型转换时报错

```java
Mono<String> stringMono = client.get()
                .uri("/webflux/hi")
				.bodyToMono(String.class);
				···
```

- 执行调用，获取响应数据

经过我的测试，我发现真正调用服务器是在我们执行`block`方法之后，之前的操作都是未来配置客户端请求数据。

```java
String block = stringMono.block();
```

##### post客户端配置

对于`post`配置，出来我们前面和`get`一样的配置外（`get`的都支持），它还支持以下参数：

- 设置请求头类型

```java
Mono<String> stringMono2 = client.post()
                .uri("/webflux/hi")
                .acceptCharset(Charset.defaultCharset())
                .header("token:1234567")
				.contentType(MediaType.APPLICATION_JSON)
```

- 设置其他请求头信息

```java
Mono<String> stringMono2 = client.post()
                .uri("/webflux/hi")
				.headers(httpHeaders -> {
                    httpHeaders.add(HttpHeaders.ACCEPT_CHARSET, "UTF-8");
                })
```

好了，客户端的相关配置就先到这里吧，下面简单测试下。

#### 测试

下面我们分别运行`get`和`post`两种客户端，访问我们的两个接口，看下效果。

首选是我们的接口，第一个是`get`接口，第二个是`post`接口：

![](https://gitee.com/sysker/picBed/raw/master/images/20210729201601.png)

接口路由配置

![](https://gitee.com/sysker/picBed/raw/master/images/20210729201714.png)

测试代码如下

```java
 public static void main(String[] args) {
        WebClient client = WebClient.create("http://localhost:8999");
        Mono<String> stringMono = client.get()
                .uri("/webflux/hi")
                .acceptCharset(Charset.defaultCharset())
                .header("token:1234567")
                .cookie("syske", "yyds")
                .attribute("name", "syske")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class);
        String block = stringMono.block();
        System.out.println("返回结果：" + block);

        Mono<String> stringMono2 = client.post()
                .uri("/webflux/hi2")
                .acceptCharset(Charset.defaultCharset())
                .header("token:1234567")
                .headers(httpHeaders -> {
                    httpHeaders.add(HttpHeaders.ACCEPT_CHARSET, "UTF-8");
                })
                .contentType(MediaType.APPLICATION_JSON)
                .cookie("syske", "yyds")
                .attribute("name", "syske")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class);
        String block1 = stringMono2.block();
        System.out.println("block1" + block1);
    }
```

然后运行下看看：

![](https://gitee.com/sysker/picBed/raw/master/images/20210729202900.png)

![](https://gitee.com/sysker/picBed/raw/master/images/20210729202951.png)

可以看到我们前面设置的数据都可以可到，比如`cookie`、`contentType`。

下面是运行结果：

![](https://gitee.com/sysker/picBed/raw/master/images/20210729203120.png)

![](https://gitee.com/sysker/picBed/raw/master/images/20210729203151.png)

### 总结

今天时间有点紧张，搞了一天企业微信`isv`环境，现在环境还是时好时坏，而且中午时间也比较紧张，所以`webflux`的相关内容也就没梳理完，不过`webfluxClient`也算基本上分享完了，明天继续来分享`webflux`其他相关知识。

