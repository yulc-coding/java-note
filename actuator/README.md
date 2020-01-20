![](https://github.com/yulc-coding/java-note/blob/master/QR_code.png)

# Actuator
监控SpringBoot项目的情况

[官网](https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-features.html)

[API](https://docs.spring.io/spring-boot/docs/2.2.3.RELEASE/actuator-api/html/)

### 基础信息
* http默认只公开了 health 和 info
* exclude 优先级大于 include

****

### 访问验证
* 公开系统注意保护自己的端点
* 定义一个`EndpointFilter `来实现实现自己的策略
* 如果引用了`Spring Security` ，则默认是使用`Spring Security`的安全策略
* 跨域设置


### 通过http进行监和管理
* 自定义路径
```
management.endpoints.web.base-path=/manage
```
* 自定义端口
```
management.server.port=9001
```
* 调整默认映射路径
```
management.endpoints.web.path-mapping.health=healthcheck
```
* 限制访问地址
```
management.server.address=127.0.0.1
```
* 禁用HTTP端点
```
management.server.port=-1
# 或者
management.endpoints.web.exposure.exclude=*
```

****

### 4 Conditions Evaluation Report (conditions)
显示SpringBoot配置和自动装配类上的条件，以及满足或者不满足条件的原因
```
http://localhost:8080/actuator/conditions
```
* positiveMatches           满足条件的类或者方法
* negativeMatches           未满足条件的类或者方法
* unconditionalClasses      无需条件判断的自动配置类的名称


### 5 Configuration Properties (configprops)
提供`@ConfigurationProperties`注解的Bean信息
```
http://localhost:8080/actuator/configprops
```
* prefix        配置前缀
* properties    参数的键值对

### 6 Environment (env)
env端点提供应用程序相关环境的信息

```
http://localhost:8080/actuator/env
```

### 8 Health (health) 实用！！！
提供有关应用程序运行状况的详细信息
```
http://localhost:8080/actuator/health
```

参数                     | 描述
---                      | --- 
status                   | 应用的总体状态
components               | 构成应用健康信息的所有组件
components.*.status      | 特定组件的状态
components.*.components  | 构成特定组件健康信息的内嵌组件列表
components.*.details     | 特定组件的详细状态

通过`management.endpoint.health.show-details`设置是否显示详情，默认不显示：
* never             不显示，默认
* when_authorized   通过授权的用户显示
* always            一直显示
 
可以查询指定的组件的健康信息，如查看数据库信息
```
http://localhost:8080/actuator/health/db
```

### 9 Heap Dump (heapdump)  分析用
提供来自应用程序JVM的堆转储信息
```
http://localhost:8080/actuator/heapdump
```
返回的是HPROF格式的二进制数据，并且可能很大。通常将数据保存到磁盘后再分析

### 10 HTTP Trace (httptrace) 分析用
显示HTTP跟踪信息（默认情况下，最近100个HTTP请求-响应交换）
```
http://localhost:8080/actuator/httptrace
```

### 11 Info (info)
提供应用程序的基本信息。Spring boot 提供了`build `和`git`部分
```
http://localhost:8080/actuator/info
```


### Log File (logfile)  鸡肋 ！！！
对日志文件内容的访问
> 当使用第三方日志，如logback或者log4j2的时候不可用
```
http://localhost:8080/actuator/logfile
```
通过在`header`中使用`Range`来查询部分内容，例如只查询日志文件的前1024字节：
```
Range: bytes=0-1023 
```

### Loggers (loggers)  实用 ！！！
访问日志信息和设置日志级别
#### 获取所有日志信息
```
http://localhost:8080/actuator/loggers
```
* configuredLevel   日志配置级别
* effectiveLevel    日志的有效级别
* groups            组信息

#### 可以获取指定的单个日志
```
# 格式： /actuator/loggers/{logger.name}

http://localhost:8080/actuator/loggers/ROOT
```
#### 可以获取指定组的日志信息
```
# 格式： /actuator/loggers/{group.name}

http://localhost:8080/actuator/loggers/web
```

#### 设置日志级别
通过POST请求传递一个JSON格式去设置。

* 设置指定日志的级别：
```
# 格式： /actuator/loggers/{logger.name}

http://localhost:8080/actuator/loggers/ROOT

{
    "configuredLevel": "DEBUG"
}
```

* 设置指定组的日志级别：
```
# 格式： /actuator/loggers/{group.name}

http://localhost:8080/actuator/loggers/web

{
    "configuredLevel": "DEBUG"
}
```

### Mappings (mappings)
映射信息
```
http://localhost:8080/actuator/mappings
```

### Metrics (metrics)
指标信息
```
http://localhost:8080/actuator/metrics
```


### Scheduled Tasks
定时任务信息
```
http://localhost:8080/actuator/scheduledtasks
```

### Shutdown (shutdown)
关闭系统，发送POST请求
```
http://localhost:8080/actuator/shutdown
```

### Thread Dump (threaddump) 有用！！！查看线程阻塞
```
查看JVM中的线程信息
```
