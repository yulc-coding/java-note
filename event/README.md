# 事件Event

### 详细介绍
[Spring Boot 实现异步事件Event](https://juejin.im/post/5e0df527e51d4540e63d844e)

### 定义事件类
继承 ApplicationEvent

### 定义监听类
实现 ApplicationListener

### 发送事件
ApplicationEventPublisher.publishEvent()

### 实现异步
* @EnableAsync 启用Spring的异步方法执行功能
* @Async 当前方法使用异步

### 自定义异步线程池