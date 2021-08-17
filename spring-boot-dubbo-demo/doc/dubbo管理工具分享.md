# dubbo管理工具分享

### 前言

在`dubbo`的应用体系中，一直都有一款图形化的`rpc`管理工具，通过这款管理工具，我们可以对我们的`rpc`服务进行各种管理操作，包括负载均衡、权重调整、服务监测等，今天我们就先来简单看下这一款管理工具。

### 管理工具

`dubbo`的管理服务，现在也是一个独立的应用，我们可以将它独立部署。项目地址如下：

```
https://github.com/apache/dubbo-admin
```

根据官方介绍，目前的管理控制台已经发布` 0.1 `版本，结构上采取了前后端分离的方式，前端使用 `Vue `和 `Vuetify `分别作为 `Javascript `框架和`UI`框架，后端采用 `Spring Boot` 框架。既可以按照标准的 Maven 方式进行打包，部署，也可以采用前后端分离的部署方式，方便开发，功能上，目前具备了服务查询，服务治理(包括 `Dubbo 2.7 `中新增的治理规则)以及服务测试三部分内容。

下面，我们看下如何在本地安装部署`dubbo`的管理控制台。

#### 下载

首先，我们要下载`dubbo-admin`的源码包或者部署包，如果下载源码包的话，需要你自己打包，由于又是前后端分离的项目，所以这里我就偷个懒，直接下载部署包：

![](https://gitee.com/sysker/picBed/raw/master/images/20210815180225.png)

![](https://gitee.com/sysker/picBed/raw/master/images/20210815180355.png)

#### 修改配置

下载完成后，直接解压，然后进入`bin\config`文件夹，这里有`dubbo-admin`的配置文件。因为后端是`spring boot`项目，所以这里的`application.properties`文件就是我们`spring boot`的配置文件，默认情况下，整个配置文件只有一些简单配置项：

```properties
admin.registry.address=zookeeper://127.0.0.1:2181
admin.config-center=zookeeper://127.0.0.1:2181
admin.metadata-report.address=zookeeper://127.0.0.1:2181

# nacos config, add parameters to url like username=nacos&password=nacos
#admin.registry.address=nacos://127.0.0.1:8848?group=DEFAULT_GROUP&namespace=public
#admin.config-center=nacos://127.0.0.1:8848?group=dubbo
#admin.metadata-report.address=nacos://127.0.0.1:8848?group=dubbo

#group (Deprecated it is recommended to use URL to add parameters,will be removed in the future)
#admin.registry.group=dubbo
#admin.config-center.group=dubbo
#admin.metadata-report.group=dubbo

#namespace used by nacos. (Deprecated it is recommended to use URL to add parameters,will be removed in the future)
#admin.registry.namespace=public
#admin.config-center.namespace=public
#admin.metadata-report.namespace=public

admin.root.user.name=root
admin.root.user.password=root

#session timeout, default is one hour
admin.check.sessionTimeoutMilli=3600000


# apollo config
# admin.config-center = apollo://localhost:8070?token=e16e5cd903fd0c97a116c873b448544b9d086de9&app.id=test&env=dev&cluster=default&namespace=dubbo

# (Deprecated it is recommended to use URL to add parameters,will be removed in the future)
#admin.apollo.token=e16e5cd903fd0c97a116c873b448544b9d086de9
#admin.apollo.appId=test
#admin.apollo.env=dev
#admin.apollo.cluster=default
#admin.apollo.namespace=dubbo

#compress
server.compression.enabled=true
server.compression.mime-types=text/css,text/javascript,application/javascript
server.compression.min-response-size=10240
```

因为没有项目的端口配置，所以默认情况下，`dubbo-admin`的访问端口是`8080`，当然你也可以自行修改；

配置文件中，首先是注册中心、配置中心、元数据中心的配置，今天我们演示的注册中心是`zk`，所以这里就不修改了；

紧接着是管理平台的登录用户名和密码，默认都是`root`，你也可以根据自己的需要修改；

最后面是压缩的相关配置，这里应该是请求响应内容的压缩配置，主要是针对`css/js`以及页面等内容。

#### 启动

完成以上内容配置，我们就可以启动测序下了。首先，我们要先启动`zk`，然后启动我们的管理平台，直接访问我们`dubbo-admin`的服务地址即可，我配置的端口是`8001`，所以我访问的地址是`http://localhost:8001`，然后会自动跳转到登录页面：

![](https://gitee.com/sysker/picBed/raw/master/images/20210815182040.png)

输入我们配置的用户名和密码，即可登录成功：

![](https://gitee.com/sysker/picBed/raw/master/images/20210815182129.png)

这个页面和我们之前分享的`k8s`的官方管理控制台很像。左侧是菜单栏，右侧是操作区域。

#### 测试演示

控制台启动成功后，我们启动本地的`dubbo`项目，进行一些简单操作。

在服务查询菜单下，我们可以看到我们刚刚启动好的服务，我们可以对这些服务进行测试、查看 详情以及其他操作。

![](https://gitee.com/sysker/picBed/raw/master/images/20210815182639.png)

这里我们就只演示下测试，因为路由、黑白名单、负载均衡、权重这些，我们目前还没深入了解过。

##### 服务测试

![](https://gitee.com/sysker/picBed/raw/master/images/20210815182827.png)

输入请求参数，点击执行，即可显示调用结果，这对我们平时觉得`rpc`不好测试的小伙伴简直就是福音，我们再也不用为`rpc`的测试而发愁了：

![](https://gitee.com/sysker/picBed/raw/master/images/20210815182927.png)

##### 接口文档

接口文档这块需要引入`dubbo-api-docs-annotations`和`dubbo-api-docs-core`的相关引用，同时要在我们的接口上添加相应的注解，今天由于时间的关系，我们暂时就不演示了，后面我们会专门分享。

##### 服务mock

写过单元测试的小伙伴应该对`mock`不陌生，这里应该也是一样的，主要是对服务的一些模拟调用，但是现在好像还不支持。

![](https://gitee.com/sysker/picBed/raw/master/images/20210815183629.png)

##### 服务统计

![](https://gitee.com/sysker/picBed/raw/master/images/20210815184213.png)

服务统计这里包括两块功能，一个是服务统计，可以展示服务的提供者、消费者、线程等信息

![](https://gitee.com/sysker/picBed/raw/master/images/20210815184412.png)

### 扩展

当然，除了`dubbo-admin`这样的官方管理平台之外，`dubbo`还可以集成`skywalking`这样的第三方运维监控平台。`skywalking`的用法我们直接已经分享过了，和`dubbo`集成应该也很容易，后面有机会的话，我们可以捎带分享下，关于`skywalking`的内容，可以点击下面的链接回顾：



### 总结 

`dubbo-admin`作为`dubbo`应用的控制管理平台，有着其强大的功能，特别是对`rpc`服务的监测、监控、以及相关配置的管理，更是一枝独秀。当然，我们今天分享的内容很简单，但是通过今天的内容，我也意识到对于`dubbo`应用的探索和学习，我觉得自己才刚刚起步，对于负载均衡、路由等相关知识，确实存在很多认知缺陷，但找到前进的方向有何尝不是一件值得庆幸的事呢？



