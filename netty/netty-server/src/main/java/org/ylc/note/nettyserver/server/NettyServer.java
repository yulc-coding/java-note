package org.ylc.note.nettyserver.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.ylc.note.nettyserver.handler.NettyServerHandler;

/**
 * 代码全万行，注释第一行
 * 注释不规范，同事泪两行
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020-03-25
 */
public class NettyServer {

    public void bind(int port) {
        // 服务端接受客户端的连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 进行 SocketChannel 的网络读写
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            // 启动 NIO 服务的引导类
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChildChannelHandler());
            // 绑定端口，同步等待成功。返回 ChannelFuture 用于异步操作的通知回调
            ChannelFuture future = bootstrap.bind(port).sync();
            // 等待服务监听端口关闭
            future.channel().closeFuture().sync();
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
        protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline().addLast(new NettyServerHandler());
        }
    }

    public static void main(String[] args) {
        new NettyServer().bind(8080);
    }
}
