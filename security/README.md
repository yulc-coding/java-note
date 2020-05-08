### 登录
* 登录成功将用户相关信息（userId,username等）生成`token`
* 将`token`存入`Redis`，`userId`作为`key`，并设置过期时间
* 查询用户的所有权限，并将权限列表存入`Redis`
* 返回`token`给前端


### token过滤器，校验token和用户权限
* 从`Request`中获取`token`
* 解析`token`获取`userId`
* 从`redis`中获取`token`，不存在说明已经过期了，提醒重新登录
* 从`redis`中获取当前用户的权限列表
* 生成一个`UsernamePasswordAuthenticationToken`放入`Context`中

