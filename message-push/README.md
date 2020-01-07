# Spring Boot 消息实时推送

### websocket
双向通讯
* 注入`ServerEndpointExporter`
* 加上`@ServerEndpoint`注解，设置WebSocket连接点的服务地址。
* 创建`AtomicInteger`用于记录连接数
* 创建`ConcurrentHashMap`用于存放连接信息
* `@OnOpen`注解表明该方法在建立连接后调用
* `@OnClose`注解表明该方法在断开连接后调用
* `@OnError`注解表明该方法在连接异常调用
* `@OnMessage`注解表明该方法在收到客户端消息后调用
* 创建推送信息的方法
* 创建移除连接的方法


### SseEmitter
Spring mvc 4.2 开始提供。服务器向客户端单向发送
* 创建`AtomicInteger`用于记录连接数
* 创建`ConcurrentHashMap`用于存放连接信息
* 建立连接：创建并返回一个带有超时时间的`SseEmitter`给前端。超时间设为0表示永不过期
* 设置连接结束的回调方法`completionCallBack`
* 设置连接超时的回调方法`timeoutCallBack`
* 设置连接异常的回调方法`errorCallBack`
* 创建推送信息的方法`SseEmitter.send()`
* 创建移除连接的方法