![](https://github.com/yulc-coding/java-note/blob/master/QR_code.png)

## 分布式锁
### 详细介绍
[Redis —— 分布式锁](https://juejin.im/post/5e1977dd6fb9a02fbb76e8eb)

### 特性
* 互斥性：只能有一个客户端持有锁
* 防死锁：客户端在持有锁期间崩溃，未能解锁，也有其他方式去解锁，不影响其他客户端获取锁
* 只有加锁的人才能释放锁

### 获取锁
使用以下指令：
```
SET mylock userId NX PX 10000
```
* mylock为锁对应的key
* userId为唯一的用户标识，用于删除时校验
* NX表示只有当key不存在时才能set成功，确保只有一个客户端能够请求成功
* PX 10000表示这个锁有一个10秒的自动过期时间

### 释放锁
当业务完成后删除key来释放锁，可以执行以下lua脚本:
```
if redis.call("get",KEYS[1]) == ARGV[1] then
    return redis.call("del",KEYS[1])
else
    return 0
end
```
执行以上脚本时，需要将`mylock`作为`KEYS[1]`传进去，将`userId`作为`ARGV[1]`传进去

### Redisson实现
* 需要引入Redisson依赖
```
<dependency>
    <groupId>org.redisson</groupId>
    <artifactId>redisson-spring-boot-starter</artifactId>
    <version>${version}</version>
</dependency>
```

* 配置连接属性
```
RedissonClient
```

* 使用方式
```
RLock lock = redissonClient.getLock(LOCK_KEY);
...
lock.unlock();
```

