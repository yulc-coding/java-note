package org.ylc.note.netty.websocket.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 消息处理器
 *
 * @author Yulc
 * @date 2021/1/11
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class SocketMessageHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 建立连接时触发
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info("新增客户端：{}", ctx.channel().localAddress());
        channelGroup.add(ctx.channel());
        sendMessage(ctx.channel(), "connect success!");
    }

    /**
     * 断开连接时触发
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        log.info("客户端[{}]断开连接", ctx.channel().localAddress());
        channelGroup.remove(ctx.channel());
    }

    /**
     * 服务端接受客户端消息
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame textWebSocketFrame) {
        String clientMsg = textWebSocketFrame.text();
        log.info("客户端【{}】发送信息：{}", ctx.channel().localAddress(), clientMsg);
    }

    /**
     * 给客户端发送消息
     */
    private void sendMessage(Channel channel, String message) {
        channel.writeAndFlush(new TextWebSocketFrame(message));
    }

    /**
     * 批量发送
     *
     * @param message 消息
     */
    public void sendMessageBatch(String message) {
        channelGroup.writeAndFlush(new TextWebSocketFrame(message));
    }
}
