# spring-cloud-Hystrix监控面板

### 前言

昨天我们分享了`Hystrix`熔断的相关知识点，但由于时间的关系，还有一些基础内容没有来得及分享，今天我们花一点时间补充下。

今天我们补充的内容主要是关于`Hystrix`监控面板的，这一块虽然不算核心内容，但是也比较重要。好了，下面我们直接开始吧。

### Hystrix控制面板

首先你需要创建一个`spring-boot`项目，或者用我们之前的项目也可以，然后添加`hystrix-dashboard`相关依赖。

#### 依赖

依赖文件也比较少，就一个`pom`依赖

```xml
 <dependency>
     <groupId>org.springframework.cloud</groupId>
     <artifactId>spring-cloud-starter-netflix-hystrix-dashboard</artifactId>
     <version>2.2.9.RELEASE</version>
</dependency>
```

但是你需要在断路器服务中添加`actuator`依赖，默认情况下是没有这个依赖的，这个依赖主要是为了监控`spring boot`服务的监控状况。

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

如果这个包添加成功后，访问服务的`/actuator`，应该是有数据正常返回的：

![](https://gitee.com/sysker/picBed/raw/master/images/20210805133236.png)

#### 项目配置

添加完依赖之后，我们还需要进行一些简单配置，才能启用`HystrixDashboard`，不过启用的方式很简单，你只需要在项目主入口类加上`@EnableHystrixDashboard`注解即可，这种配置方式也算是`Spring-boot`配置的常规操作了。

```java
@SpringBootApplication
@EnableHystrixDashboard
public class SpringCloudHystrixDashboardDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudHystrixDashboardDemoApplication.class, args);
    }

}
```

#### 添加监控服务

以上配置工作完成后，访问如下地址即可访问熔断器管理页面

```
http://localhost:9991/hystrix
```

页面效果如下

![](https://gitee.com/sysker/picBed/raw/master/20210805084625.png)

然后我们需要在页面上配置我们需要访问的服务，下面我们简单介绍下`Hystrix Dashboard`的简单用法。

##### 服务地址

开始之前我们先介绍下页面上的文字描述，这些提示信息主要是告诉我们地址栏如何输入的，第一行提示的意思是，如果我们要访问`Turbine`默认集群的话，访问地址如下：

```
https://turbine-hostname:port/turbine.stream
```

第二行提示的意思是，如果我们要访问指定`turbine`集群的话，访问地址如下：

```
https://turbine-hostname:port/turbine.stream?cluster=[clusterName]
```

最后一行的意思是如果我们要访问单节点的`Hystrix`服务的话，访问地址如下：

```
https://hystrix-app:port/actuator/hystrix.stream
```

这里简单介绍下，`turbine`是`Netflix`提供了一个开源项目，主要是为了方便我们把多个`hystrix.stream`的内容聚合为一个数据源供`Dashboard`展示。我们这里只是一个单点`Hystrix`，所以地址栏直接填写第三个地址就可以了：

```
http://localhost:9991/actuator/hystrix.stream
```

##### 刷新时间

页面上的`Delay`就是请求间隔时间，单位是`ms`，时间间隔越小，数据越详细，但是被监控的服务请求压力也越大

##### 标题

页面上的`Title`设置的是我们监控页面显示的标题，这个可以根据自己的情况填写

填写完成后，直接点击底下`Monitor Stream`按钮就可进入监控页：

![](https://gitee.com/sysker/picBed/raw/master/20210805083421.png)

##### 扩展

我用浏览器访问了下我们刚刚输入的监控地址，发现它的请求方式就是`text/event-stream`，也就是`webflux`，这也是`webflux`最典型的应用场景。

![](https://gitee.com/sysker/picBed/raw/master/images/20210805133435.png)

这也表面，如果你觉得`hystrix-dashboard`面板做的丑的话，你是可以自定义监控页面的，直接调用`actuator/hystrix.stream`接口即可。

#### 踩坑

这里是可能遇到的问题，也算是我踩坑过程的一些记录，如果遇到问题，可以参考解决。

##### 提示Unable to connect to Command Metric Stream

如果访问之后，你的页面有如下提示：

![](https://gitee.com/sysker/picBed/raw/master/image-20210805083752789.png)

这时候你需要先看下后端控制台提示信息

###### 404错误

这个错误通常是因为你的监控地址填写不正确，好好检查下应该就可以解决

![](https://gitee.com/sysker/picBed/raw/master/images/20210805131629.png)

如果检查完，发现地址也没有问题，那应该是因为断路器服务未添加`actuator`的依赖导致的，你只需要添加下面的依赖即可：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```



###### 后台提示is not in the allowed

如果控制台有如下提示信息，表面你没有配置允许访问熔断监控页面的地址：

![](https://gitee.com/sysker/picBed/raw/master/20210805084005.png)

你只需要在你的`hystrix-dashBoard`配置文件中添加如下配置即可：

```properties
hystrix.dashboard.proxy-stream-allow-list= localhost
```

##### 页面一直Loading

如果一直如下显示，这是因为你的服务一直没有被访问，所以没有监控数据

![](https://gitee.com/sysker/picBed/raw/master/image-20210805083500577.png)

只要你调用一下服务中用到断路器的接口，就会看到监控数据：

![](https://gitee.com/sysker/picBed/raw/master/20210805083421.png)

### 总结

好了，`hystrix`相关的知识点暂时就分享到这里，后面有机会的话，我们再来剖析`hystrix`的其他相关知识。

最近这几天一直忘记分享`demo`项目的地址了，今天一并发出，刚兴趣的小伙伴可以去看下：

- `spring-cloud`之服务治理组件`Eureka`

  ```
  https://github.com/Syske/learning-dome-code/tree/dev/spring-cloud-eureka-demo
  ```

- `spring-cloud`服务治理组件`Eureka`客户端（包括`feign`和`ribbon`两种）

  ```
  https://github.com/Syske/learning-dome-code/tree/dev/spring-cloud-client-demo
  ```

- `spring-cloud`整合`hystrix`断路器

  ```
  https://github.com/Syske/learning-dome-code/tree/dev/Spring-cloud-hystrix-demo
  ```

- `spring-cloud`整合`hystrix`监控面板

  ```
  https://github.com/Syske/learning-dome-code/tree/dev/spring-cloud-hystrix-dashboard-demo
  ```



有兴趣的小伙伴可以关注下这个项目，我所有的学习`demo`都在，之前推送到的内容相关代码也存在这个仓库下，目前已经有`60`多个项目，还在持续更新

- 项目地址

  ```
  https://github.com/Syske/learning-dome-code
  ```

- 项目截图

![](https://gitee.com/sysker/picBed/raw/master/images/20210805185936.png)

