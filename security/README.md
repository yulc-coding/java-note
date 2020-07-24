## Spring Security + Token

### 流程
> 登录成功并根据用户ID生成token返回前端，并将token存入Redis缓存中，设置过期时间。查询用户所有的权限，并将权限列表放入Redis缓存中，过期时间同token。

> 访问接口时传入Token，系统校验解析Token获取用户ID，校验Redis中Token是否过期，重置Token过期时间，重置权限列表过期时间。

### SecurityUserDetails
用于鉴权的用户信息


### SecurityUserService
获取用户信息:
* 登录获取用户信息
* 访问接口获取用户信息（校验token）


### CustomUsernamePasswordAuthenticationFilter
登录拦截器，可以用于校验用户、验证码


### AuthorizationTokenFilter
访问接口时进行鉴权：校验token


### AccessDecisionService
动态URL权限校验：判断当前路劲在Redis权限列表中是否存在
