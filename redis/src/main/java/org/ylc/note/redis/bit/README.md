![](https://github.com/yulc-coding/java-note/blob/master/QR_code.png)

## 位图
最大可以设置2^32个不同的位，即512MB

### 详细介绍
[Redis —— 位图Bitmaps](https://juejin.im/post/5e1a81295188252c33052045)

位图的存储大小计算： （maxOffset / 8 / 1024 / 1024）MB。其中maxOffset为位图的最大位数

### 基本用法
#### SETBIT key offset value
设置指定key的值在offset处的bit值，offset从0开始。返回值为在offset处原来的bit值
```
# 通过位操作将 h 改成 i
127.0.0.1:6379> SET h h         # 二进制为 01101000
OK
127.0.0.1:6379> SETBIT h 7 1    # 将最后一位改成1 => 01101001
(integer) 0
127.0.0.1:6379> GET h
"i"
```

#### GETBIT key offset
获取指定key的值在offset处的bit值，offset从0开始。如果offset超出看当前位图的范围，则返回0。
```
127.0.0.1:6379> set i i       # 二进制为 01101001
OK
127.0.0.1:6379> getbit i 0    # 第1位为0
(integer) 0
127.0.0.1:6379> getbit i 1    # 第2位为0
(integer) 1
127.0.0.1:6379> getbit i 7    # 第8位为0
(integer) 1
```

#### BITCOUNT key [start end]
统计指定key值中被设置为1的bit数。可以通过指定参数star和end来限制统计范围。
> 注意，这里的star和end不是指bit的下标，而是字节(byte)的下标。比如start为1，则实际对应的bit下标为8（1byte = 8 bit）

```
127.0.0.1:6379> set hi hi           # 二进制为 0110100001101001
OK
127.0.0.1:6379> bitcount hi         # 所有是1的位数：7个
(integer) 7
127.0.0.1:6379> bitcount hi 1 2     # 即统计 01101001 中1的位数
(integer) 4
```

#### BITPOS key bit [start] [end]
统计首次出现的0或1的bit位，可以通过start和end来指定范围，同样是指字节的下标。
* 在不存在的key或者空字符串中查找1，则返回-1
* 在所有bit都为1中查找bit为0的情况下，返回字符串最右边的第一个空位

```
    127.0.0.1:6379> get nilkey           # 不存在的key
    (nil)
    127.0.0.1:6379> bitpos nilkey 1      # 在不存在的key中查首次出现1的位
    (integer) -1
    127.0.0.1:6379> setbit nilkey 0 0    # 空字符串
    (integer) 0
    127.0.0.1:6379> get nilkey
    "\x00"
    127.0.0.1:6379> bitpos nilkey 1
    (integer) -1
```

#### BITOP operation destkey key [key ...]
对一个或多个二进制位字符串进行操作，并将结果保存到 destkey 上。当某个字符串长度不够时，对应的位用0补上
* AND（逻辑与）：都为1返回1，否则返回0
```
    127.0.0.1:6379> set a a                  # 二进制  01100001
    OK
    127.0.0.1:6379> set c c                  # 二进制  01100011
    OK
    127.0.0.1:6379> bitop and destkey a c    # 与操作  01100001 -> a
    (integer) 1
    127.0.0.1:6379> get destkey
    "a"
```

* OR（逻辑或）：只要有一个1就返回1，否则返回0
```
    127.0.0.1:6379> set a a                 # 二进制  01100001
    OK
    127.0.0.1:6379> set b b                 # 二进制  01100010
    OK
    127.0.0.1:6379> bitop or destkey a b    # 或操作  01100011 -> c
    (integer) 1
    127.0.0.1:6379> get destkey
    "c"
    127.0.0.1:6379>     
```

* XOR（逻辑异或）：当都是0或者都是1时返回0，否则返回1
```
    127.0.0.1:6379> set a a                 # 二进制  01100001
    OK
    127.0.0.1:6379> set z Z                 # 二进制  01011010 (大写的Z)
    OK
    127.0.0.1:6379> bitop xor destkey a z   # 异或    00111011 -> ; 分号
    (integer) 1
    127.0.0.1:6379> get destkey
    ";"
```

* NOT（逻辑非）：取反，1变成0，0变成1。**只能传入一个要操作的key**
```
    01010101 -> 10101010
```

### 应用场景
* 用户签到
* 统计用户上线次数
* 用户活跃度
* 用户在线状态