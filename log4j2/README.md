# Spring Boot 使用 log4j2 记录日志

### 详细介绍
[Spring Boot Log4j2 日志简单使用](https://juejin.im/post/5dccb7a0518825599c24769e)

> Spring Boot 默认使用 **Logback** 作为日志框架，所以使用时需要去除对 **Logback** 的依赖  
> 如果需要实现异步日志，需要引入 LMAXDisruptor 

#### 功能点：
* 不同级别生成不同日志文件；
* 设置单个日志文件的大小；
* 按日期生成日志文件；
* 生成自定义类别日志文件；
* 设置指定包或者类的日志输出级别；
* 异步记录日志
 