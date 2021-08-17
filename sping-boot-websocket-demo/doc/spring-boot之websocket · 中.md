# spring-boot之websocket · 中

### 前言

昨天我们提到，并不是所有的浏览器都支持`websokcet`协议，对于不支持的浏览器，我们要通过`STOMP `协议来进行兼容，今天我们就来看下如何通过`STOMP `来兼容`websocket`。

### websocket兼容

`STOMP`的全称是`Simple (or Streaming) Text Orientated Messaging Protocol`，中文的意思是简单(流)文本定向消息协议，也就是说，我们其实使用了消息组件来兼容的。

#### 配置类

对于不支持`websocket`的浏览器我们需要通过`STOMP`来兼容，兼容的解决方案涉及两方面知识，一个是`SockJs`，一个就是`WebSocketMessageBroker`。`SockJs`一种让前端可以支持`socket`通信的技术解决方案，`WebSocketMessageBroker`是基于消息组件实现的一种通信协议。

下面是我们的`STOMP`解决方案的配置类，注释已经够详细了，所以这里就不在赘述。

```java
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * 注册服务器端点
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 增加一个聊天服务端点
        registry.addEndpoint("/socket").withSockJS();
        // 增加一个用户服务端点
        registry.addEndpoint("/wsuser").withSockJS();
    }

    /**
     * 定义服务器端点请求和订阅前缀
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 客户端订阅路径前缀
        registry.enableSimpleBroker("/sub", "/queue");
        // 服务端点请求前缀
        registry.setApplicationDestinationPrefixes("/request");
    }
}
```

#### 消息接收接口

这里定义两个接口，一个是接收通用消息的（`/send`），一个是发给指定用户的（`/sendToUser`）。这里需要补充说明的是，`@SendTo`注解的作用是将接收到的消息发送到指定的路由目的地，所有订阅该消息的用户都能收到，属于广播。

```java
@RestController
public class WebsocketController {
    private final Logger logger = LoggerFactory.getLogger(WebsocketController.class);

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private WebSocketService webSocketService;

    @MessageMapping("/send")
    @SendTo("/sub/chat")
    public String sendMessage(String value) {
        logger.info("发送消息内容：{}", value);
        return value;
    }

    @MessageMapping("/sendToUser")
    public void sendToUser(Principal principal, String body) {
        String srcUser = principal.getName();
        String[] args = body.split(": ");
        String desUser = args[0];
        String message = String.format("【%s】给你发来消息：%s", webSocketService.getNameMap().get(srcUser), args[1]);
        // 发送到用户和监听地址
        simpMessagingTemplate.convertAndSendToUser(desUser, "/queue/customer", message);

    }

}
```

#### 前端页面

##### 普通消息发送

首先要引入`jquery.js`、`stomp.js`和`sockjs.js`，这个三个`js`就可以确保前端页面也支持`STOMP`协议。

然后我们定义了三个方法：`connect()`、`disconnect()`和`sendMessage()`方法。

在`connect`方法内部，我们通过`SockJS`初始化了`stompClient`实例，`SockJS`的节点地址就是我们配置类中定义的聊天服务节点，然后建立`stomp`连接。

发送消息的时候，我们直接调用`stomp`客户端的`send`方法即可，这里需要指定发送消息的地址，要和消息接收方的地址一致。

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>websocket STOMP</title>
</head>
<body>
websocket兼容STOMP测试<br>
<div>
    <div>
        <button id = "connect" onclick="connect()">连接</button>
        <button id = "disconnect" disabled="disabled" onclick="disconnect()">断开连接</button>
</div>
    <div id = "conversationDiv">
        <p>
            <label>发送消息内容</label>
        </p>
        <p>
            <textarea id="message" rows = "5"></textarea>
        </p>
        <p>
            <button id = "sendMsg" onclick="sendMessage()">发送</button>
        </p>
        <p id = "response">

        </p>
    </div>

    <a href="#" target="/websocket-receive">跳转到消息接收页</a>
</div>

<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
<script src="https://cdn.bootcdn.net/ajax/libs/stomp.js/2.3.3/stomp.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
<script type="text/javascript">
    var stompClient = null;
    // 设置连接
    function setConnected(connected) {
        $("#connect").attr({"disabled": connected});
        $("#disconnect").attr({"disabled": !connected});
        if (connected) {
            $("#conversationDiv").show();
        } else {
            $("#conversationDiv").hide();
        }
        $("#response").html("")
    }

    function connect() {
        // 定义请求服务器的端点
        var socket = new SockJS('/socket');
        // stomp客户端
        stompClient = Stomp.over(socket);
        // 连接服务器端点
        stompClient.connect({}, function (frame) {
            // 建立连接后的回调
            setConnected(true);
        })
    }
    // 断开socket连接
    function disconnect() {
        if (stompClient != null) {
            stompClient.disconnect();
        }
        setConnected(false);
        console.log("Disconnected");
    }
    // 向/request/send服务端发送消息
    function sendMessage() {
        var message = $("#message").val();
        // 发送消息到"/request/send"，其中/request是服务器定义的前缀
        // 而/send则是@MessageMapping所配置的路径
        stompClient.send("/request/send", {}, message);
    }
    connect();
</script>

</body>
</html>
```

##### 普通文本消息接收

接收页面和发送页面对应，`sockJS`的地址必须一样，因为是接收消息，所以这里执行的是`stompClient`的`subscribe`（订阅消息），这里的地址也必须和发送页面一致，否则无法收到消息

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>websocket-stomp-receive</title>
</head>
<body>

<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
<script src="https://cdn.bootcdn.net/ajax/libs/stomp.js/2.3.3/stomp.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
<script type="text/javascript">
    var noticeSocket = function () {
        // 连接服务器端点
        var s = new SockJS('/socket');
        //客户端
        var stompClient = Stomp.over(s);
        stompClient.connect({}, function () {
            console.log("notice socket connected ！");
            // 订阅消息地址
            stompClient.subscribe('/sub/chat', function (data) {
                $('#receive').html(data.body);
            });
        });
    };
    noticeSocket();
</script>
<h1><span id="receive">等待接收消息</span></h1>

</body>
</html>
```

##### 普通文本测试

我登陆了两个账号，用其中一个账号发送消息，他自己以及另一个账号都收到了发送的消息，说明我们的实例是`ok`的。

![](https://gitee.com/sysker/picBed/raw/master/images/20210726132032.png)

下面，我们看下如何给指定用户发送消息。

##### 给指定用户发送消息

发送页面没有区别，只是`js`不一样，所以这里只贴出`js`。

首先第一个不一样的地方是服务端点不一样了，我们这里的`SockJS`监听的是`/wsuser`，也就是给指定用户发送消息的地址。

然后再就是发送消息的地址也变了，指定的是`/request/sendToUser`，对应的是指定用户的发送消息的接口，剩下其他的都一模一样。

```java
<script type="text/javascript">


    var stompClient = null;
    // 设置连接
    function setConnected(connected) {
        $("#connect").attr({"disabled": connected});
        $("#disconnect").attr({"disabled": !connected});
        if (connected) {
            $("#conversationDiv").show();
        } else {
            $("#conversationDiv").hide();
        }
        $("#response").html("")
    }

    function connect() {
        // 定义请求服务器的端点
        var socket = new SockJS('/wsuser');
        // stomp客户端
        stompClient = Stomp.over(socket);
        // 连接服务器端点
        stompClient.connect({}, function (frame) {
            // 建立连接后的回调
            setConnected(true);
        })
    }
    // 断开socket连接
    function disconnect() {
        if (stompClient != null) {
            stompClient.disconnect();
        }
        setConnected(false);
        console.log("Disconnected");
    }
    // 向/request/send服务端发送消息
    function sendMessage() {
        var message = $("#message").val();
        var user = $("#user").val();
        // 发送消息到"/request/send"，其中/request是服务器定义的前缀
        // 而/send则是@MessageMapping所配置的路径
        var messageSend = user + ": " + message
        stompClient.send("/request/sendToUser", {}, messageSend);
    }
    connect();
</script>
```

##### 给指定用户接收页面

这里也只是`js`发生变化，节点名称和发生页面一致，订阅地址和配置类中的一致。

```js
<script type="text/javascript">
    var noticeSocket = function () {
        // 连接服务器端点
        var s = new SockJS('/wsuser');
        //客户端
        var stompClient = Stomp.over(s);
        stompClient.connect({}, function () {
            console.log("notice socket connected ！");
            // 订阅消息地址
            stompClient.subscribe('/user/queue/customer', function (data) {
                $('#receive').html(data.body);
            });
        });
    };
    noticeSocket();
</script>
```

##### 给指定用户发送消息测试

这次我们用哪吒的账号给女娲发了一条消息，最终的结果是只有女娲收到了消息，也和我们预期一致。

![](https://gitee.com/sysker/picBed/raw/master/images/20210726133342.png)

### 总结

相比于昨天我们直接通过`websocket`通信，通过`STOMP`通信，前端要稍过复杂一些，但总体来说，也不是特别复杂。

通篇来看，其实`STOMP`就是后端启动一个消息池，然后将消息发送接口暴露给前端，前端调用发送消息接口发消息，消息由后端转发到消息池中指定的队列（类似消息中继站），然后消费者（订阅该队列的消息接收方）接收并消费其中的消息。

如果知道了这点，那我们完全可以自己根据`mq`的相关文档开发一套，而且现在好多`mq`都提供了对`ajax`的支持，比如`activemq`。

