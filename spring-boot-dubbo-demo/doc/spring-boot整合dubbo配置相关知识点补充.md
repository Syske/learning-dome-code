# spring-boot整合dubbo相关知识点补充

### 前言

昨天我们分享了`spring-boot`整合`dubbo`的相关内容，不过准确地说，应该是和`spring `整合，因为我们其实并没有用`dubbo`的`starter`，而是通过注解的方式把`dubbo`的相关配置注入到`spring`的`ioc`容器中。

这种方式只是省去了`xml`配置的繁琐配置，改成了注解的方式，本质上只能算`web`层面的集成，因为`spring-boot`的核心在`stater`，`starter`的核心在自动配置，关于定制自己的`spring-starter`我们前面有分享过，感兴趣的小伙伴可以去看下：

不过，这对不喜欢`xml`配置的小伙伴来说（我算其中有一个，当然`xml`有其优势），已经算福音了。好了，废话就说这么多，今天我们来对昨天的内容做一个简单的补充说明，由于昨天的内容过于繁杂，而且内容有点多，好多细节的知识点，没有在展开说明，下面我们就来对这些知识点做一个说明。

### dubbo内容补充

#### 配置

`dubbo`的配置主要有三部分，一部分是注册中心的相关配置，一部分是应用本身的配置，另外一部分是注册中心的配置。今天主要说明前两种配置，注册中心的配置暂时先不讲。

##### 服务注册配置

服务注册的配置还是比较多的，下面是`RegistryConfig`的属性：

```java
 	// 注册中心地址
    private String address;
	// 注册中心用户名
    private String username;
	// 注册中心登录密码
    private String password;
	// 注册中心端口
    private Integer port;
	// 注册中心协议
    private String protocol;
	// 网络传输类型
    private String transporter;
	// 具体作用不详
    private String server;
	// 注册中心客户端类型
    private String client;
    /**
    * 集群类型
    * 影响流量在注册中心之间的分布方式，在订阅多个注册中心时非常有用，可用选项：
    * 1.zone-aware，根据流量的来源，特定类型的流量始终进入一个注册表。
    */
    private String cluster;
    /**
     * 注册中心所属的区域，通常用于隔离流量
     */
    private String zone;
    /**
     * 服务所属组
     */
    private String group;
	/**
	* 服务版本
	*/
    private String version;
    /**
     * 注册中心请求超时时间，单位毫秒
     */
    private Integer timeout;
    /**
     * 注册中心会话超时时间，单位毫秒
     */
    private Integer session;
    /**
     * 保存注册中心动态列表的文件
     */
    private String file;
    /**
     * 停止服务等待时间
     */
    private Integer wait;
    /**
     * 启动时是否检查注册中心是否可用
     */
    private Boolean check;
    /**
     * 是否允许动态服务在注册中心注册
     */
    private Boolean dynamic;
    /**
     * 是否在注册中心导出服务
     */
    private Boolean register;
    /**
     * 是否允许在注册中心订阅服务
     */
    private Boolean subscribe;
    /**
     * 自定义参数
     */
    private Map<String, String> parameters;

    /**
     * 是否为默认注册中心
     */
    private Boolean isDefault;
    /**
     * 简化注册。对提供者和使用者都很有用
     *
     * @since 2.7.0
     */
    private Boolean simplified;
    /**
     * 简化注册表后，应单独添加一些参数。仅限服务提供者
     * <p>
     * such as: extra-keys = A,b,c,d
     *
     * @since 2.7.0
     */
    private String extraKeys;
    /**
     * 该地址是否作为配置中心工作
     */
    private Boolean useAsConfigCenter;
    /**
     * 该地址是否用作远程元数据中心
     */
    private Boolean useAsMetadataCenter;
    /**
     * 此注册中心接受的rpc协议列表，例如，“dubbo，rest”
     */
    private String accepts;
    /**
     * 如果设置为true，则始终首先使用此注册中心，在订阅多个注册表时非常有用
     */
    private Boolean preferred;

    /**
    * 请求权重（集群）
	* 影响注册中心间的流量分布，在订阅多个注册中时非常有用
	* 仅在未指定首选注册表时生效。
	*/
    private Integer weight;
```

以上配置只有`address`和`client`是必须配置的，其余的都是可以不配置的，下面我们选一些常用的属性简单介绍下：

- `address`：注册中心地址，以`zk`为例的话，就是：`zookeeper://127.0.0.1:2181`；

- `client`：客户端类型，对`2.7.1`之后得版本，如果注册中心是`zk`d的话，只能是`curator`

- `username`和`password`：这个应该不用解释了，`zk`应该不用配置这个

- `port`：这个端口，目前没发现有啥用，毕竟地址里面已经包含端口了

- `protocol`：注册中心支持的协议类型，我发现`dubbo`支持的类型还挺多的，包括`dubbo`、`rest`、`http`、`redis`等，具体大家可以看下图：

  ![](https://gitee.com/sysker/picBed/raw/master/images/20210813125641.png)

- `transporter`：网络传输协议也比较多，默认应该是`netty`，因为`dubbo`默认引入了`netty`的包

  ![](https://gitee.com/sysker/picBed/raw/master/images/20210813125752.png)

  ![](https://gitee.com/sysker/picBed/raw/master/images/20210813130011.png)

- `group`：注册的服务默认情况下（不指定`group`）所属服务组。通常我们在注册服务的时候，会指定服务所属服务组，如果你不指定所属服务组，则会取当前设置的值，这个配置的默认值目前还没研究

  ![](https://gitee.com/sysker/picBed/raw/master/images/20210813131101.png)

- `version`：这个配置和`group`类似，不过这个设定的是服务的版本，如果不指定就会取该值，这个值可以在服务注册时设定，但是设定的只是单个服务的版本

  ![](https://gitee.com/sysker/picBed/raw/master/images/20210813130947.png)

- `timeout`：这个设置的是`rpc`请求的超时时间，也是个默认值，这个值可以在服务注册的时候指定，如果你不指定，就会取这个值

  ![](https://gitee.com/sysker/picBed/raw/master/images/20210813130913.png)

##### 应用配置

```java

    /**
     * 应用名称
     */
    private String name;

    /**
     * 应用版本
     */
    private String version;

    /**
     * 应用所属者
     */
    private String owner;

    /**
     * 应用所属组织
     */
    private String organization;

    /**
     * 应用架构
     */
    private String architecture;

    /**
     * 环境 例如： dev 、test 或者 production
     */
    private String environment;

    /**
     * Java 编译版本
     */
    private String compiler;

    /**
     * 日志等级
     */
    private String logger;

    /**
     * 注册中心列表
     */
    private List<RegistryConfig> registries;
    private String registryIds;

    /**
     * 监控中心
     */
    private MonitorConfig monitor;

    /**
     * 是否为默认
     */
    private Boolean isDefault;

    /**
     * thread dump保存地址
     */
    private String dumpDirectory;

    /**
     * 是否启用qos
     */
    private Boolean qosEnable;

    /**
     * qos监听主机地址
     */
    private String qosHost;

    /**
     * qos监听端口
     */
    private Integer qosPort;

    /**
     * 是否允许外网访问
     */
    private Boolean qosAcceptForeignIp;

    /**
     * 自定义参数
     */
    private Map<String, String> parameters;

    /**
     * 服务停止等待时间
     */
    private String shutwait;

    private String hostname;

    /**
     * 元数据类型： local 或者 remote, 如果选择remote, 还需要指定元数据中心
     */
    private String metadataType;

    private Boolean registerConsumer;

    private String repository;
```

应用配置，只有`name`是必须的，其他都是非必须的。注释也很详细了，这里就不作过多说明了。

##### 消费者配置

消费者的配置和提供者配置一样，所以这里我们就不再赘述。

#### 注入配置

前天我们的演示实例中，我们是直接把配置项写死的，今天我们来看下如何通过配置文件设定我我们的配置。

首先，我们在`spring-boot`的`appliaction.properties`中加入`dubbo`的配置：

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
#application.dubbo.registry.port=20880
#application.dubbo.registry.protocol=20880
#application.dubbo.registry.transporter=20880
#application.dubbo.registry.cluster=20880d
#application.dubbo.registry.zone=20880d
#application.dubbo.registry.group=20880d
#application.dubbo.registry.version=1.0
#application.dubbo.registry.timeout=60000
#application.dubbo.registry.session=30000
#application.dubbo.registry.file=20880d
#application.dubbo.registry.wait=30000
#application.dubbo.registry.check=true
#application.dubbo.registry.dynamic=true
#application.dubbo.registry.register=true
#application.dubbo.registry.subscribe=true
#application.dubbo.registry.parameters.name=syske
#application.dubbo.registry.default=true
#application.dubbo.registry.simplified=true
#application.dubbo.registry.extraKeys=true
#application.dubbo.registry.useAsConfigCenter=true
#application.dubbo.registry.useAsMetadataCenter=true
#application.dubbo.registry.accepts=true
#application.dubbo.registry.preferred=true
#application.dubbo.registry.weight=10
```

然后在`dubbo`的配置类，加入如下代码：

```java
@Bean
@ConfigurationProperties(ignoreUnknownFields = false, prefix = "application.dubbo.application")
public ApplicationConfig applicationConfig() {
    ApplicationConfig applicationConfig = new ApplicationConfig();
    return applicationConfig;
}

@Bean
@ConfigurationProperties(ignoreUnknownFields = false, prefix = "application.dubbo.registry")
public RegistryConfig registryConfig() {
    RegistryConfig registryConfig = new RegistryConfig();
    return registryConfig;
}
```

这里简单介绍下，`@ConfigurationProperties`注解可以和`@Bean`组合使用，这样在创建`bean`的时候，就可以把我们的配置文件直接注入到我们`bean`的属性中，确实也很方便。

需要注意的是，`prefix`的前缀必须与`properties`中的配置相对应，否则配置无法被注入。

另一个需要注意的是，如果配置项是`isXXX`，则需要确认`isXXX`的设置方法是否是`setIsXXX`，如果是，`properties`配置就可以写成`isXXX`，总之要和`set`方法一致，否则会报错：

![](https://gitee.com/sysker/picBed/raw/master/images/20210813135825.png)

#### 注解

注解这块主要有两个注解注解比较重要，一个`@DubboService`，一个是`@Reference`。

##### DubboService

`@DubboService`注解是`2.7.7`引入的，其主要作用就是为了标记和配置服务提供者，它的前任是`@Service`，这个注解也算是个新人，它是`2.7.0`引入的，从注解属性上看，他们没有本质区别，`@Service`目前已经被弃用，我猜测弃用应该是注解名称太容易被混淆了，不利于服务代码开发维护，毕竟`spring`的原生注解也就`@Service`：

![](https://gitee.com/sysker/picBed/raw/master/images/20210812125920.png)

文档也说的很清楚，`DubboService`是`Serivce`的继任者。

![](https://gitee.com/sysker/picBed/raw/master/images/20210812130021.png)

##### DubboReference

`@Reference`注解也是`dubbo 2.7.7`引入的，主要是用来发现服务的，也就是标记服务消费者。这个注解的继任者是`Reference`，也是`2.7.0`引入的。

![](https://gitee.com/sysker/picBed/raw/master/images/20210812190902.png)

![](https://gitee.com/sysker/picBed/raw/master/images/20210812190159.png)

说明，在`dubbo 2.7.0`之前的版本是不支持注解式配置，而且我发现`2.7.0`以前的版本是属于`com.alibaba`这个`groupId`的，之后的版本是`org.apache.dubbo`这个`groupId`的，这是因为在`2.7.0`之后，阿里巴巴把`dubbo`捐献给`apache`基金会了，现在它是`apache`旗下的顶级项目之一。

![](https://gitee.com/sysker/picBed/raw/master/images/20210812191439.png)

![](https://gitee.com/sysker/picBed/raw/master/images/20210812191522.png)



### 总结

`dubbo`的知识点还是比较多的，算上今天我们补充的内容，我们目前已经分享的内容也只能算冰山一角的一角，但是到今天我们也算对`dubbo`有了一个最基本的认知了，从项目创建，到项目启动，再到项目配置，整个流程我们应该已经比较熟悉了，如果有小伙伴已经忘记了，可以回顾下之前的内容，至于其他更复杂的配置和高级用法，我们在后面的学习过程中，会继续分享，继续深挖。好了，今天的内容就到这里吧！