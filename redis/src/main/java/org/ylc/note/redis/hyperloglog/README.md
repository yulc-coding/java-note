![](https://github.com/yulc-coding/java-note/blob/master/QR_code.png)

## 基数统计：HyperLogLog
### 简介
`HyperLogLog`是`Redis`中的高级数据结构，它主要用于对海量数据（可以统计**2^64**个数据）做基数统计（去重统计数量）。它的特点是速度快，占用空间小（**12KB**）。但是计算存会在误差，标准误差为**0.81%**。`HyperLogLog`只会根据输入元素来计算基数，而不会储存输入元素本身，所以他并不能判断给定的元素是否已经存在了。

### 基本指令

#### pfadd(key,value...)
将指定的元素添加到`HyperLogLog`中，可以添加多个元素

#### pfcount(key...)
返回给定`HyperLogLog`的基数估算值。当一次统计多个`HyperLogLog`时，需要对多个`HyperLogLog`结构进行比较，并将并集的结果放入一个临时的`HyperLogLog`，性能不高，谨慎使用

#### pfmerge(destkey, sourcekey...)
将多个`HyperLogLog`进行合并，将并集的结果放入一个指定的HyperLogLog中

### 应用场景
* 网站UV统计