# spring-boot-websocket总结回顾

### 前言

`websocket`的内容比我预期的要少，本来打算分三次分享，但是经过昨天和前天的分享，核心内容已经完结了，所以我们今天来做一个简单小结。

### websocket总结回顾

相比于我们前两天刚分享的`security`，`websocket`确实比较少，不过它内容虽然不多，但是这项技术却很有用，比如你要搭建一个个人聊天系统或者做一个客服组件，那`websocket`就是最佳选择，而且兼容性方便也是没得说，根据我的实际测试`IE11`本身是支持`websocket`，如果老一点的`IE`，我们也可以通过`STOMP`来兼容。

今天我们的核心内容也是从一张脑图开始，公众号后台回复【spring-websocket】获取脑图源文件：

![](https://gitee.com/sysker/picBed/raw/master/images/20210727124514.png)

#### 核心依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-websocket</artifactId>
</dependency>
```



#### 核心配置

- 创建一个服务节点池，本质上它就是一个节点容器

```java
@Bean
public ServerEndpointExporter serverEndpointExporter() {
    return new ServerEndpointExporter();
}
```



#### 节点服务

##### @ServerEndpoint

- 需要指定节点地址，客户端根据节点地址与服务端通信

```java
@ServerEndpoint("/ws")
@Service
public class WebSocketService {
...
}
```



#### 监听方法

- `@OnOpen`：客户端建立连接时会调用该方法

  - 参数

    - `Session`

  ```java
  @OnOpen
  public void onOpen(Session session) {
      String name = nameMap.get(session.getUserPrincipal().getName());
      this.session = session;
      webSocketServiceSet.add(this);
      addOnlineCount();
      logger.info("有新连接加入！当前在线人数为: {}", onlineCount.get());
      webSocketServiceSet.parallelStream().forEach(item -> {
          try {
              sendMessage(item.getSession(), String.format("%s加入群聊！", name));
          } catch (Exception e) {
              logger.error("发送消息异常：", e);
          }
      });
  }
  ```

  

  

- `@OnMessage`：客户端发送消息时调用该方法

  - 参数

    - `message`
    - `Session`


  ```java
  @OnMessage
  public void onMessage(String message, Session session) {
      logger.info("来自客户端的消息：{}", message);
      webSocketServiceSet.parallelStream().forEach(item -> {
          String name = nameMap.get(session.getUserPrincipal().getName());
          logger.info("{}发送了一条消息：{}", name, message);
          try {
              item.sendMessage(item.getSession(), String.format("%s:%s", name, message));
          } catch (IOException e) {
              e.printStackTrace();
          }
      });
  }
  ```

  

- `@OnClose`：客户端关闭`websocket`连接时回调该方法

  - 参数

    - `Session`


  ```java
  @OnClose
  public void onClose(Session session) {
      logger.info("{}退出群聊！", session.getUserPrincipal().getName());
      webSocketServiceSet.remove(this);
      subOnlineCount();
  }
  ```

  

- `@OnError`：客户端发生错误时调用该方法

  - 参数

    - `Session`
    - `Throwable`


  ```java
  @OnError
  public void onError(Session session, Throwable t) {
      logger.error("发生错误：", t);
  }
  ```

  

#### 前端

##### 创建websocket对象

```js
var websocket = new WebSocket("ws://localhost:8989/ws")
```



##### 指定回调函数

- `open`

  ```js
  websocket.onopen = function () {
  // 方法实现
  }
  ```

  

- `error`

  ```js
  websocket.onerror = function () {
  // 方法实现
  }
  ```

  

- `message`

  ```js
  websocket.message = function () {
  // 方法实现
  }
  ```

  

- `close`

  ```js
  websocket.onclose = function () {
  // 方法实现
  }
  ```

  

##### 发送消息

```js
websocket.send(message)
```



#### STOMP兼容

对于不支持`websocket`的浏览器，我们需要通过`stomp`协议进行兼容，但是目前我接触到的浏览器包括`IE`都是支持`websocket`的。

##### 配置类

- 注解

  - `@EnableWebSocketMessageBroker`

    作用：启用`websocketMessageBroker`

- 配置方法

  - `registerStompEndpoints()`

    - 注册节点

      ```java
      registry.addEndpoint("/socket").withSockJS()
      ```

      

  - `configureMessageBroker()`：

    - 指定客户端订阅路径前缀

      ```java
      registry.enableSimpleBroker("/sub", "/queue")
      ```

      

    - 指定服务端点请求前缀

      ```java
      registry.setApplicationDestinationPrefixes("/request")
      ```

      

##### 接口

- 发送广播消息

  ```java
  @MessageMapping("/send")
  @SendTo("/sub/chat")
  public String sendMessage(String value) {
      logger.info("发送消息内容：{}", value);
      return value;
  }
  ```

  

- 发送私有消息

  ```java
  @MessageMapping("/sendToUser")
  public void sendToUser(Principal principal, String body) {
      String srcUser = principal.getName();
      String[] args = body.split(": ");
      String desUser = args[0];
      String message = String.format("【%s】给你发来消息：%s", webSocketService.getNameMap().get(srcUser), args[1]);
      // 发送到用户和监听地址
      simpMessagingTemplate.convertAndSendToUser(desUser, "/queue/customer", message);
  
  }
  ```

  

##### 前端

- 引入js包

  - `stomp.js`
  - `sockjs.js`

- 建立`STOMP`连接

  - 创建`SockJS`实例

    ```js
    var socket = new SockJS('/socket');
    ```

    

  - 创建`stompClient`

    ```js
    var stompClient = Stomp.over(socket);
    ```

    

  - 建立连接

    ```js
    stompClient.connect({}, function (frame) {
        // 建立连接后的回调
        setConnected(true);
    })
    ```

    

- 断开`stomp`连接

  ```js
  stompClient.disconnect()
  ```

  

- 发送消息

  ```js
  stompClient.send("/request/send", {}, message)
  ```

  

- 接收方订阅消息

  ```js
  stompClient.connect({}, function () {
      console.log("notice socket connected ！");
      // 订阅消息地址
      stompClient.subscribe('/sub/chat', function (data) {
          $('#receive').html(data.body);
      });
  });
  ```

  

### 总结

`websocket`的内容相对比较简单，而且配置也不复杂，所以今天也没有太多要补充说明的，各位小伙伴如果对于之前的知识点还有疑问，可以再回顾下：

