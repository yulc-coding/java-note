package org.ylc.note.nettyserver.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ylc.note.nettyserver.handler.WebSocketServerHandler;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * WebSocket
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020/4/6
 */
public class WebSocketServer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void run(int port) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChildChannelHandler());

            Channel channel = bootstrap.bind(port).sync().channel();
            logger.info("Web Socket server started at port {}", port);
            logger.info("http://localhost:{}/", port);
            channel.closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 优雅的退出，释放线程池资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private static class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel ch) {
            // 将请求和应答消息编码或者解码为 HTTP 消息
            ch.pipeline().addLast("http-codec", new HttpServerCodec());
            // 将 HTTP 的多个部分组合成一条完整的 HTTP 消息
            ch.pipeline().addLast("aggregator", new HttpObjectAggregator(65536));
            // 支持浏览器和服务端进行 WebSocket 通信
            ch.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
            ch.pipeline().addLast("handler", new WebSocketServerHandler());
        }
    }


    public static void main(String[] args) {
        new WebSocketServer().run(8080);
    }
}
