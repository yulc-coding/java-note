## Netty相关
 


### TCP粘包/拆包导致读半包或者多包问题

#### StringDecoder
将`ByteBuf`解码成字符串

#### LineBasedFrameDecoder
将回车换行符`\n`或者`\r\n`作为消息结束，即按行读取

#### DelimiterBasedFrameDecoder
以分隔符作为结束标志的消息的解码
```
ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
```
> 参数1024表示单条消息的最大长度，当到达长度后还没有查找到分隔符就会报异常
>
> 第二个参数就是分隔符缓冲对象

#### FixedLengthFrameDecoder
对固定长消息的解码
```
ch.pipeline().addLast(new FixedLengthFrameDecoder(20));
```