package com.hanggle.demo.netty.cs;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 当通道就绪时触发该方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client:" + ctx);
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello server: this is client", CharsetUtil.UTF_8));
    }

    /**
     * 当通道可以读取时
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        String s = byteBuf.toString(CharsetUtil.UTF_8);
        System.out.println("client receive server info:" + s);
        System.out.println("client receive server ctx:" + ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
