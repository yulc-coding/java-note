# Spring Boot 使用 Logback 记录日志

## 详细介绍
[Spring Boot Logback 日志简单使用](https://juejin.im/post/5dca73cef265da4d4b5ff30f)

## 功能点
* 按日志级别生成文件

* 自定义生成日志文件：

    1. 定时任务等需要独立分析的日志存放独立文件

    2. 将指定包或者类的日志生成独立的文件

* 按日期生成文件

* 设定日志文件大小

* 设定日志文件过期时间

## 开始
* Spring Boot 默认使用了Logback作为日志框架，所以不需要引入其他jar包  
* 在 src/main/resources 下新建XML文件 logback-spring.xm  

## 基本结构
```
<configuration>

  <!-- 定义变量  通过 ${name} 来使用 -->
  <property name="", value =""></property>

  <!-- 日志相关配置 -->
  <appender></appender>

  <!-- 设置具体的打印，并引用appender -->
  <logger></logger>

 <!-- 功能同logger， 是所有logger的上级节点 -->
  <root></root>

</configuration>
```

## 运行单元测试
