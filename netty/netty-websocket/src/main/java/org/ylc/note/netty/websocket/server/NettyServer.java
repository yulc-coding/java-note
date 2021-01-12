package org.ylc.note.netty.websocket.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.ylc.note.netty.websocket.handler.SocketMessageHandler;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 服务端
 * <p>
 * webSocket测试地址：
 * http://www.easyswoole.com/wstool.html
 *
 * @author Yulc
 * @date 2021/1/11
 */
@Slf4j
@Component
public class NettyServer {

    @Value("9000")
    private int port;

    @Autowired
    private SocketMessageHandler socketMessageHandler;

    EventLoopGroup bossGroup = new NioEventLoopGroup();
    EventLoopGroup workerGroup = new NioEventLoopGroup();

    @PostConstruct
    public void start() throws Exception {

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .localAddress(port)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        log.info("收到新的连接信息：【{}】", socketChannel.localAddress());
                        socketChannel.pipeline()
                                // Http 解码器
                                .addLast("codec", new HttpServerCodec())
                                // 分块处理
                                .addLast("chunked", new ChunkedWriteHandler())
                                // HTTP 消息聚合
                                .addLast("aggregator", new HttpObjectAggregator(65536))
                                // webSocket 相关
                                .addLast(new WebSocketServerProtocolHandler("/socketServer", "WebSocket", true, 65536 * 10))
                                // 自定义业务逻辑处理器
                                .addLast("socketHandler", socketMessageHandler);
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 1024);
        // 端口绑定
        ChannelFuture future = bootstrap.bind().sync();
        log.info("webSocket server started, Listening on : {}", port);
        // 关闭服务器通道 会 wait
        // future.channel().closeFuture().sync();
    }


    @PreDestroy
    public void destroy() {
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }
}
