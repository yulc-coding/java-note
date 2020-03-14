# SpringBoot Starter

### 详细介绍
[创建自己的SpringBoot Starter](https://juejin.im/post/5e03753ff265da33d83e80f4)

### autoconfigure模块
* 参数配置类（XXXProperties）
* 具体业务实现类（需要被注入到容器的Bean）
* 自动配置类（XXXAutoConfiguration）
* 条件化配置(Condition)
* 重点：配置spring.factories（/resources/META-INF下）

### starter模块
* 提供了对autoconfigure模块的依赖
* 代码自动提示：spring-boot-configuration-processor
* 过滤加载：spring-boot-autoconfigure-processor
