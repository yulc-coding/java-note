# 事件Event

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