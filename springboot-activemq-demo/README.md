# springboot整合ActiveMQ实现异步交易

## 前言

前段时间，我们分享了`ActiveMQ`的一些基本知识，介绍了`ActiveMQ`的简单部署和基本用法，演示了`java`环境使用`ActiveMQ`收发消息的简单操作，但当时只讲了`ActiveMQ`，`demo`也不是`web`项目，距离我们实际应用确实也比较远，为了让各位小伙伴更够更直观地了解`ActiveMQ`的实际应用场景，体会到异步交易的魅力，今天我们通过一个小小的`demo`，来看下`springboot`和`ActiveMQ`的整合应用。

今天的核心知识点就两个：

- `Springboot`异步交易
- `springboot`整合`ActiveMQ`

好了，话不多说，我们直接开始。

## 正文

我们的内容，是以文件异步导出业务为例写的一些业务代码。我先简单说下业务处理过程，第一步，用户发起文件导出请求，后端接收到前端请求后，验证请求参数，并发起异步文件导出交易，交易发起成功后返回结果。

第二步，导出成功后，用户可以在文件下载中心进行下载。

![](https://gitee.com/sysker/picBed/raw/master/images/20210523151728.png)

为了演示方便，我把所有数据都存放在`reids`里面了，一般实际项目中会把文件信息存放在数据库中，处理成功后才会放进缓存。项目的完整源码附在文末，有兴趣的小伙伴自己去看。

### 启用JMS

创建项目，我们这里就不介绍，到今天还不会搭建`springboot`开发环境，确实该面壁思过了。项目创建完成后，在`springboot`入口加上如下配置启用`jms`(`java message servic`)：

```
@EnableJms
```



### 引入依赖

除了`spring-boot-starter-web`，这里我们还需要引入如下依赖：

```xml
<dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-starter-activemq</artifactId>
</dependency>

<dependency>
    <groupId>org.apache.activemq</groupId>
    <artifactId>activemq-pool</artifactId>
    <version>5.15.10</version>
</dependency>
<!-- 在2.X版本,spring.activemq.pool.enabled=true时,需依赖该jar -->
<dependency>
    <groupId>org.messaginghub</groupId>
    <artifactId>pooled-jms</artifactId>
    <version>1.0.3</version>
</dependency>
```

这里简单说明下，第一个依赖是`activemq`的`starter`，是`activemq`组件的核心依赖，所有的组件都是基于他展开的；

第二个依赖是`activemq`的连接池，类似于数据库连接池；

第三个依赖是`activemq`自动配置类依赖的包。

后面两个依赖是可选的，如果你启用了`activeMQ`连接池（`spring.activemq.pool.enabled=true`时），那你就必须依赖，没有依赖的话，`sprinbgoot`启动会报错：

![](https://gitee.com/sysker/picBed/raw/master/images/20210523134208.png)

主要原因是`activemq`的自动配置时依赖了这个包，没有这个包`Jms`的连接工厂是无法被初始化的：

![](https://gitee.com/sysker/picBed/raw/master/images/20210523134319.png)

![](https://gitee.com/sysker/picBed/raw/master/images/20210523134425.png)

有兴趣的小伙伴可以自己把这个依赖先拿掉试下。

### 添加配置

完成上面的工作，我们要启动本地的`ActvieMQ`服务，然后添加`ActvieMQ`配置信息：

```properties
spring.activemq.broker-url=tcp://127.0.0.1:61616
spring.activemq.pool.enabled=true
spring.activemq.pool.max-connections=100
```

如果不需要连接池，后面两个配置可以直接拿掉。

### 消息发送接口

发送接口 就是消息的生产者，`springboot`提供了消息的模板类（`JmsMessagingTemplate`），我们可以通过`Autowired`注入使用：

```java
@Service
public class JmsSendService {
    @Autowired
    private JmsMessagingTemplate jmsTemplate;

    public void sendMessage(String queueName, String message) {
        jmsTemplate.convertAndSend(queueName, message);
    }
}
```

`ActiveMQ`支持有返回值和无返回值两种会话形式，你可以根据自己的需要选择，`JmsMessagingTemplate`都是支持的，提供的模板方法也比较丰富：

![](https://gitee.com/sysker/picBed/raw/master/images/20210523141400.png)

这里我们只用到了`convertAndSend`，字面意思就是方法的意思，`object`是消息内容，`destination`是消息队列名称，看下源码你就知道，方法内部会把我们的消息内容转换成`Message`对象，当然如果你有特殊需求，你也可以自己组装`Message`，只是过程比较繁琐，简单业务的话，用我这种方式就比较简便了。

如果你需要接收返回值，那你可以调用`sendAndReceive(Message<T> var)`接口来实现，但是需要你自己定义自己的`Message<T>`，需要实现`Message<T>`接口。

```java
class StringMessage implements Message<String> {

    private String payload;
    private MessageHeaders messageHeaders;

    public StringMessage(String payload) {
        this.payload = payload;
    }

    @Override
    public String getPayload() {
        return this.payload;
    }

    @Override
    public MessageHeaders getHeaders() {
        return this.messageHeaders;
    }
}
```

调用`sendAndReceive`：

```java
public String sendAndReceive(String queueName, String message) {
        Message<?> messageBack = jmsTemplate.sendAndReceive(queueName, new StringMessage(message));
        return (String)messageBack.getPayload();
    }
```

`JmsMessagingTemplate`其实就是`springboot`抽象出来的一个通用的消息发送模板，它理论上是可以支持所有`mq`的，只需要官方提供`starter`即可，对开发者来说，确实比较友好，只需要修改配置，剩下的就不用管了，很方便有木有。

这里是`servicce`层的实现过程：

```java
/**
     * 文件导出
     * @param name
     * @param userId
     * @return
     */
public JSONObject export(String userId, String name) {

    JSONObject result = new JSONObject();
    result.put("userId", userId);
    result.put("type", 0);
    String uuId = UUIDUtil.getUUId();
    result.put("fileId", uuId);
    result.put("name", name);
    // 异步导出文件
    doExport(result);
    result.put("success", true);
    result.put("code", 0);
    result.put("message", "数据导出提交成功，请稍后到文件中心下载！");
    return result;
}
```

### springboot异步交易

导出文件方法`doExport`内部，我们使用了多线程异步交易，这样的好处是把业务逻辑都放进异步交易中处理，可以将响应结果更快地呈现给用户，让接口响应更快。这里我们插个楼，讲一些`springboot`异步线程池的用法。

#### 启用异步交易

`springboot`启动异步交易很简单，只需要在项目入口加上`@EnableAsync`即可

#### 添加异步线程池配置

配置线程池大小

```java
@Configuration
public class ExcuterConfig {
    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(150);
        executor.setQueueCapacity(500);
        return executor;
    }
}
```

#### 使用线程池

这里的`taskExecutor`就是我们前面配置的方法名。这里的异步线程和`mq`的异步交易是不一样的。线程池大小是固定的，当所有线程被阻塞，线程池队列也被占满，有新的交易进来时，线程池会因为资源耗尽报错，这时候后续业务是无法正常处理的；但是`mq`基本上是不存在阻塞资源耗尽的情况的（除非资源耗尽），特别是对于不需要有返回指定的交易，它只是一个消息仓库，只要消息不被消费，消息是可以一直存在的，也不会超时。

```java
@Async("taskExecutor")    void doExport(JSONObject jsonObject) {        try {            String name = jsonObject.getString("name");            if (StringUtils.hasLength(name)) {                String userId = jsonObject.getString("userId");                String uuId = jsonObject.getString("fileId");                // 其他数据校验，这里通过睡眠模拟                Thread.sleep(1000L);                // 组装保存文件信息                jsonObject.put("type", 0);                jsonObject.put("isDownload", false);                jsonObject.put("createTime", System.currentTimeMillis());                // 保存文件数据，实际业务中，这部分应该是存在数据库里的，这里为了演示方便，直接存在数据库里了                redisUtil.setString(String.format("fileExport.%s.%s", userId, uuId), jsonObject.toJSONString());                // 发送文件导出业务消息                jmsSendService.sendMessage("file-export-queue", jsonObject.toJSONString());            }        } catch (Exception e) {            logger.error("数据导出错误", e);        }    }
```



### 消息接收消费

这里主要是通过`@JmsListener`创建了一个消息监听器，监听`ActiveMQ`指定队列的状态，当有新的消息进来时，该方法会被执行。方法内部是我们要异步业务处理过程。针对不同的业务类别，你可以指定不同的队列名称，但是同一个业务的发送方和消费者必须是相同的队列名称，否则是无法被消费的。

```java
 @JmsListener(destination = "file-export-queue",  containerFactory = "jmsListenerContainerFactory")    public void testMq(String message) {        logger.info("文件导出业务入参：{}", message);        JSONObject messageJsonObject = JSON.parseObject(message);        Integer type = messageJsonObject.getInteger("type");        if (type == 0) {            Object fileId = messageJsonObject.get("fileId");            Object userId = messageJsonObject.get("userId");            String filePath = String.format("./%s.txt", fileId);            messageJsonObject.put("path", filePath);            String fileKey = String.format("fileExport.%s.%s", userId, fileId);            // 查询数据            List<String> dataList = Lists.newArrayList("张三", "历史", "周三");            try(FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {                for (String s : dataList) {                    fileOutputStream.write(s.getBytes(StandardCharsets.UTF_8));                    fileOutputStream.write("\n".getBytes());                }                redisUtil.setString(fileKey, messageJsonObject.toJSONString());            } catch (Exception e) {                logger.error("文件导出失败", e);            }        }
```

### 测试

到这里，我们就可以简单测试下了，我写了一个页面，两个接口。

#### 接口

```java
@GetMapping("/file/{user_id}/export")    public JSONObject fileExport(@PathVariable("user_id") String userId,                                 @RequestParam String name) {        return fileService.export(userId, name);    }    @GetMapping("/file/{user_id}/download/{file_id}")    public JSONObject download(@PathVariable("user_id") String userId,                               @PathVariable("file_id") String fileId,                               HttpServletResponse response) {        return fileService.download(userId, fileId, response);    }
```

#### 页面

这里名称随便输，数据是写死的。导出请求提交成功后，会返回文件`id`，我把文件`id`展示在页面上，点击链接就可以下载

![](https://gitee.com/sysker/picBed/raw/master/images/20210523152532.png)

## 总结

今天的内容从整体上来看，还是比较简单的，主要是`springboot`已经把好多配置工作搞好了，我们只需要简单配置即可。但是过程还是有点艰辛的，官方没有提供相关文档，网上的教程我又不想参考，所以踩了好多坑，花的时间也有点长，但是结局还是比较完美的，所有需求都实现了，而且还让我积累了整合经验。但是在整合过程中，我发现对于`ActiveMQ`配置这块，我还是比较迷，大部分的配置都不清楚，所以未来这块还需要深入去研究下。

最后，希望有兴趣的小伙伴最好自己动手实践下，毕竟实践出真知，眼睛会了，手不见得会……

今天分享内容的源码：

```
https://github.com/Syske/learning-dome-code/tree/dev/springboot-activemq-demo
```

更多java优质内容，请关注我的公众号【云中志】

<img src="https://images.cnblogs.com/cnblogs_com/caoleiCoding/921220/o_200830020106qrcode_for_gh_6985fde6e5e8_344.jpg" style="zoom:50%;" />
