package org.ylc.note.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

import java.net.InetSocketAddress;

/**
 * 代码全万行，注释第一行
 * 注释不规范，同事泪两行
 * <p>
 * HTTP服务器
 * 服务端启动过程：
 * 配置线程池
 * 初始化 Channel
 * 端口绑定
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020-10-30
 */
public class HttpServer {

    public void start(int port) throws Exception {
        /*
         * 默认会启动 2 倍 CPU 核数的线程，也可以指定线程数
         * 主从多线程 Reactor 模型
         * Boss 是主 Reactor：负责处理 Accept，然后把 Channel 注册到从 Reactor 上
         * Worker 是从 Reactor：负责 Channel 生命周期内的所有 I/O 事件。
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    // 设置 Channel 类型，推荐使用 NioServerSocketChannel
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    // 注册 ChannelHandler
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ch.pipeline()
                                    // HTTP 编解码
                                    .addLast("codec", new HttpServerCodec())
                                    // HttpContent 压缩
                                    .addLast("compressor", new HttpContentCompressor())
                                    // HTTP 消息聚合；netty是基于分段请求的，将请求分段再聚合,参数是聚合字节的最大长度
                                    .addLast("aggregator", new HttpObjectAggregator(65536))
                                    // 自定义业务逻辑处理器
                                    .addLast("handler", new HttpServerHandler());
                        }
                    })
                    // 设置 Boss 线程组 对应的Channel 参数
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    // 设置 Worker 线程组 对应的Channel 参数
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            // 端口绑定
            ChannelFuture future = bootstrap.bind().sync();
            System.out.println("Http Server started， Listening on " + port);
            future.channel().closeFuture().sync();

        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new HttpServer().start(8888);
    }

}
