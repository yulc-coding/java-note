package org.ylc.note.nettyserver.handler;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 代码全万行，注释第一行
 * 注释不规范，同事泪两行
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020-03-25
 */
public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private WebSocketServerHandshaker webSocketServerHandshaker;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 传统的 Http 接入
        if (msg instanceof FullHttpRequest) {
            handleHttpRequest(ctx, (FullHttpRequest) msg);
        } else if (msg instanceof WebSocketFrame) {
            //  WebSocket 接入
            handleWebSocketFrame(ctx, (WebSocketFrame) msg);
        }
    }

    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest request) {
        if (!request.decoderResult().isSuccess() || (!"websocket".equals(request.headers().get("Upgrade")))) {
            sendHttpRequest(ctx, request, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }
        WebSocketServerHandshakerFactory factory = new WebSocketServerHandshakerFactory(
                "ws://localhost:8080/websocket", null, false
        );
        webSocketServerHandshaker = factory.newHandshaker(request);
        if (webSocketServerHandshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            webSocketServerHandshaker.handshake(ctx.channel(), request);
        }
    }

    private void sendHttpRequest(ChannelHandlerContext ctx, FullHttpRequest request, FullHttpResponse response) {
        if (response.status().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(response.status().toString(), CharsetUtil.UTF_8);
            response.content().writeBytes(buf);
            buf.release();


        }

        ChannelFuture future = ctx.channel().writeAndFlush(response);
    }

    private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
        // 关闭指令
        if (frame instanceof CloseWebSocketFrame) {
            webSocketServerHandshaker.close(ctx.channel(), ((CloseWebSocketFrame) frame).retain());
            return;
        }
        // 是否为 ping 消息
        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(
                    new PongWebSocketFrame(frame.content().retain())
            );
            return;
        }

        // 这里只支持文本消息，不支持二进制
        if (!(frame instanceof TextWebSocketFrame)) {
            throw new UnsupportedOperationException(frame.getClass().getName() + " frame types not supported");
        }
        String request = ((TextWebSocketFrame) frame).text();
        logger.info("{} received {}", ctx.channel(), request);

        ctx.channel().write("From WebSocket Server :" + System.currentTimeMillis());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

}
