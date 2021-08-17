# spring-cloud断路器之Hystrix

### 前言

昨天我们分享了`spring-cloud`基于`feign`声明式的服务调用，演示了声明式服务调用的基本过程，虽然还有很多内容没有分享，但是现阶段仅考虑基本用法，所以更详细的内容暂时先不展开，等这一轮内容分享结束之后，我们再视情况分享。

今天我们要开始分享另一个`spring-cloud`核心组件——`Hsystrix`。`Hystrix`中文含义豪猪，但是它实际的作用和它的名称却相去甚远，在我们`spring-cloud`家族中，它的用途就是断路器，也叫熔断器。

正如，电路里面的熔断器一样，`spring-cloud`中的熔断器也起着同样的作用，有所不同的是，电路中的熔断器是根据电路中的电流大小出发的，而我们的`Hystrix`是根据服务的超时时间触发的，也就是说如果某一个服务达到了我们设定的超时时间，没有正常返回的话，就会触发我们的断路操作，然后会将短路操作的结果返回。

下面，我们通过一个具体的实例来演示下`Hystrix`的断路原理。

### Hystrix

首先我们需要先创建一个`spring-boot`项目，然后引入`hystrix`的核心依赖。这个依赖就加在我们需要熔断操作的服务的依赖中，也就是服务提供者的依赖中。

#### 依赖

下面是`hystrix`的核心依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
    <version>2.2.9.RELEASE</version>
</dependency>
```

因为我们的服务都有都是基于`eureka`注册中心调用的，所以我们也需要加入`eureka`的相关依赖和配置，具体可以参考这几天的`demo`

#### 配置

这里也是针对服务提供者的配置，在项目入口类上加上`@EnableCircuitBreaker`注解，即可启用`Hystrix`熔断器。这样，我们的服务就支持熔断操作了。

```java
@SpringBootApplication
@EnableCircuitBreaker
public class SpringCloudHystrixDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudHystrixDemoApplication.class, args);
    }

}
```

#### 接口熔断配置

这里设置的接口的配置，只需要在接收上加上`@HystrixCommand`注解即可启用断路器。

为了方便演示，我们这里生成一个随机睡眠时间，人为模拟超时情形。默认情况下，`Hystrix`的超时熔断时间为`1000ms`，即只要超过`2000ms`未正常响应，我们的超时熔断机制就会被触发，然后执行我们的熔断回调方法。

`Hystrix`的熔断超时时间是可以设置的，而且其他相关的配置项也有很多，由于篇幅和时间的原因，今天我们就先不展开讲了，后面有机会再来分享。

```java
@RequestMapping("/tetHystrix/{name}")
@HystrixCommand
public Object hystrix(@PathVariable(name = "name") String name) {
    JSONObject jsonObject = new JSONObject();
    try {
        Double v = 3000 * Math.random();
        System.out.println("name: " + name + " 睡眠时间：" + v);
        jsonObject.put("sleep", v.longValue());
        jsonObject.put("name", name);
        jsonObject.put("message", "请求成功");
        Thread.sleep(v.longValue());
    } catch (Exception e) {
        System.out.println(e);
    }
    return jsonObject;
}
```

`@HystrixCommand`注解是支持指定熔断器回调方法的，也就是熔断触发时调用的方法，如果不指定回调方法的话，默认返回的是`500`错误，下面是前端页面的返回结果：

![](https://gitee.com/sysker/picBed/raw/master/20210804083229.png)

后端也有错误抛出，我们可以看到`hsystrix timed-out and fallback failed`错误提示：

![](https://gitee.com/sysker/picBed/raw/master/20210804083200.png)

这也就是说如果启用断路器，必须指定回调方法，否则虽然也触发了熔断机制，但对调用方而言并不友好，当然如果你只是需要触发断路器，报错也无所谓，那不指定回调方法也不影响。

指定熔断回调方法也很简单，只需要在注解中指定`fallbackMethodd`的值即可，它的值就是我们回调方法名称

```java
@HystrixCommand(fallbackMethod = "error")
```

同时我们需要定义一个名字为`error`，参数与接口保持一致的熔断回调方法：

```java
public Object error(String name) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", "触发服务熔断机制");
        jsonObject.put("name", name);
        return jsonObject;
    }
```

回调方法的名字可以根据自己的需要自定义，但是参数必须与接口保持一致，否则会报错：

![](https://gitee.com/sysker/picBed/raw/master/20210804083708.png)

以上配置完成后，我们的服务提供者就配置好了，下面我们来看下调用方的一些配置。

#### 调用方设置

熔断器其实和调用方没有任何关系，所以也不需要任何配置。但是为了便于我们测试，所以我们专门写了一个测试`controller`，在`controller`内部，我们循环调用前面定义的服务提供者。

```java
@RequestMapping("/testHy")
public Object testHystrix() {
    List<JSONObject> jsonObjectList = Lists.newArrayList();
    for (int i = 0; i < 10; i++) {
        jsonObjectList.add(restTemplate.getForObject("http://spring-cloud-Hystrix-demo/tetHystrix/" + i, JSONObject.class));
    }
    return jsonObjectList;
}
```



#### 测试

测试也很简单，我们先启动服务提供者（加了熔断配置的服务提供者），然后访问我们的`/testHy`，我们可以看到，在所有请求中，只有`4`个是成功返回的，其余的都因为超时触发熔断机制。

![](https://gitee.com/sysker/picBed/raw/master/images/20210804134103.png)

关于这一点，我们从后台打印的睡眠时间也可以看出来：

![](https://gitee.com/sysker/picBed/raw/master/images/20210804134040.png)

前面我们也说了，默认情况下，超时熔断时间为`1000ms`（有的书上说是`3000`，可能是版本问题，也有可能是作者搞错了，我实测的结论是`1000ms`），如果超过这个时间，就会触发熔断机制，执行并返回熔断回调方法。

### 总结

关于`Hystrix`今天我们主要通过一个实例演示了它的用法，当然它还有很多内容，但是由于时间的关系，我们今天的内容就先到这里，后续有时间的话，我们再好好刨析`Hystrix`的实现原理。

好了，今天就到这里吧，大家晚安！