package org.ylc.note.nettyserver.handler;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
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
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private int counter;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // ByteBuf buf = (ByteBuf) msg;
        // byte[] req = new byte[buf.readableBytes()];
        // buf.readBytes(req);
        // String body = new String(req, StandardCharsets.UTF_8);
        String body = (String) msg;
        logger.info("server receive msg: [{}]; counter is [{}]", body, ++counter);

        String respStr = "From Netty Client".equals(body) ? "From Netty Server" : "Error format";
        respStr = respStr + System.getProperty("line.separator");
        ByteBuf resp = Unpooled.copiedBuffer(respStr.getBytes());
        ctx.writeAndFlush(resp);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }
}
