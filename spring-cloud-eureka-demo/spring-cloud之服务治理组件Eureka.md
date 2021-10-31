# spring-cloud之服务治理组件Eureka

### 前言

前几天我们分享了`webflux`的相关知识点，由于越分享发现内容越多，所以`webflux`的相关知识就先告一段落，但是就目前分享的内容来说，已经足够让我们对`webflux`有一个最近基本的认识，至于其他内容，我们后期继续分享。

昨天天太热了，不在状态，也脉动不回来，所以也就没有分享新的东西。

今天也热，感觉像进了烤箱一样，但是学习还是不能太懈怠，该坚持的不应该被放弃，而且这燥热的天气必须得有点产出，这样我才稍微觉得生活的有点追求，人也稍微有点精神，所以思前想后，我就决定分享下`spring cloud`的相关知识了。

`spring cloud`算是现阶段比较主流的技术，也是很多公司现阶段面试的必问内容了，早一点做知识储备，这样后面再找工作也不会太过焦虑。

学习这件事，就应该，苟日新，日日新，又日新，当然也有做好就有知识的学习。好了，话不多说，下面我们直接开始吧。

### Eureka

我们今天内容的主角就是`spring cloud`的服务注册治理组件——`Eureka`，它中文的意思是找到了，发现了，它的作用和它的名称大致相同，所以他在我们`spring cloud`中所起的作用就是服务注册和服务发现，我们今天主要分享的是`spring cloud Eureka`组件的配置和简单用法，至于服务之间的调用，我们放在后面讲解。

#### 创建项目

首先我们创建一个`spring-boot-web`项目，核心依赖就是`spring-boot-starter`:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
</dependency>
```

然后还需要引入`spring-cloud-eureka`的依赖：

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
    <version>2.2.9.RELEASE</version>
</dependency>
```

这里的版本可以在`mvn`的仓库查询，地址如下：

```
https://mvnrepository.com/search?q=spring-cloud-starter-netflix-eureka-server
```

#### eureka配置

##### 服务端配置

首先在项目主类上加上`@EnableEurekaServer`注解，启用`Eureka`服务端：

```java
@SpringBootApplication
@EnableEurekaServer
public class SpringCloudEurekaDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudEurekaDemoApplication.class, args);
    }

}
```

然后在`application.properties`文件中加入如下配置：

```properties
# Eureka 注册服务器主机名称
eureka.instance.hostname=localhost
# 是否注册给服务中心
eureka.client.register-with-eureka=false
# 是否检索服务
eureka.client.fetch-registry=false
# 治理客户端服务域
eureka.client.service-url.defaultZone=http://localhost:8999/eureka/
```

第一个配置是设定`Eureka`的服务器名称，目前没发现有啥用；

第二个配置项是设置是否注册到服务中心，这个配置默认是`true`，也就是说默认情况下会自动注册至`Eureka`，但是由于当前服务是注册中心，所以就没必要再注册了。

第三个配置项是和第二个配置相对，它是设置是否从服务注册中心获取服务（发现服务），默认情况也是`true`，由于本服务为服务中心，所以也不需要获取。

第四个配置设定的是注册中心的服务地址，客户端也需要用到这个。不过，`eureka`服务其实是不需要要这个地址的，当然配置或者不配置，服务注册中心都可以启动。

但是如果服务器配置了`defaultZone`（不论配置什么地址），那么客户端的`defaultZone`配置必须指定这个地址，而且服务的地址必须是

```
eureka服务地址:服务端口/eureka
```

否则，客户端的启动会报错：

![](https://gitee.com/sysker/picBed/raw/master/20210801184042.png)

根据错误来看，应该是服务发现的时候报错了。

##### 客户端配置

下面我们看下`eureka`客户端的配置，首先我们创建一个`spring boot`项目（这里我就直接用我之前的项目了），然后引入`eureka`客户端依赖：

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    <version>2.2.9.RELEASE</version>
</dependency>
```

然后在`application.properties`文件中添加如下配置：

```properties
eureka.client.service-url.defaultZone=http://localhost:8999/eureka/
```

这里设置的就是我们`eureka`服务的`defaultZone`地址，这个地址就是我们的服务注册地址。

如果你的服务版本比较老，那你可能还需要在`spring boot`服务入口类中添加`@EnableDiscoveryClient`注解：

```java
@SpringBootApplication
@EnableDiscoveryClient
public class DailyNoteApplication {

    public static void main(String[] args) {
        SpringApplication.run(DailyNoteApplication.class, args);
    }

}
```

这个注解的作用是启用服务发现，但是在比较新的版本下，这个配置是默认启用的（我目前的版本是`2.2.9`，是不需要加这个配置的）

##### 测试

服务配置完成后，我们分别启动`Eureka`服务和我们测试用的`spring boot`服务，为了便于观察，建议多创建几个服务，另外最好在服务中配置服务名称，这样在查看服务的时候，也便于区分：

```properties
# 应用名称
spring.application.name=spring-webflux-demo
```

启动成功后，访问`http://localhost:8999`（`eureka`服务地址），即可看到我们的服务注册情况：

![](https://gitee.com/sysker/picBed/raw/master/20210801184013.png)

中间就是我们注册的服务，这里的服务名就是我们在`properties`文件中配置的名称，状态`status`表示服务状态，`up`表示状态正常。

我们可以看到`DALIY-NOTE`服务我们注册了两个服务，所以在右侧显示了两个服务节点，点击右侧节点就可以看到服务健康信息：

![](https://gitee.com/sysker/picBed/raw/master/20210801183924.png)

因为我们配置的原因，所以数据显示是空的，但是你可以访问`http://laptop-u2emgb33:8081/actuator/`，这时候是有数据的：

![](https://gitee.com/sysker/picBed/raw/master/20210801184233.png)

这里的健康其实和`spring-cloud`没关系，是`spring boot`的监控组件，这一款我们后期专门分享吧。

如果访问提示`404`，表示你的项目缺少`actuator`监控的依赖：

![](https://gitee.com/sysker/picBed/raw/master/20210801184125.png)

添加下面的依赖，然后重新启动就好了。

```xml
<!-- actuator监控信息完善 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```



### 总结

以前对`spring cloud`的认识不够，总是分不清`spring boot`和`spring cloud`的区别，就算勉强能说上了一两点，心里总是不确定，因为对`spring cloud`确实了解的不够，这一次我的目标就是破解`spring cloud`认知壁垒，构建`spring cloud`的知识体系，健全微服务知识体系。

今天算是一个简单的入门简介，后面我们持续分享。好了，今天就先到这里吧！

