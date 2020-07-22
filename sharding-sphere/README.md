## 分表分库中间件：ShardingSphere

### 策略
* 分表分库所采用的字段：`sharding-column`
* 分表分库的规则：`algorithm-expression`

### 绑定表：`binding-tables`
* 用于相同分表字段和规则的表
* 在连表查询时可以减少路由查询次数

### 广播表：`broadcast-tables`
* 所有分片数据源中都存在的表，表结构和数据都一样
* 适用于数据量不大，但是需要和海量数据进行联查，如字典表

### 强制路由：Hint

