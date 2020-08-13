package com.hanggle.demo.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {
    /**
     *
     * @param ch
     * @throws Exception
     */
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        // 向管道加入处理器
        ChannelPipeline pipeline = ch.pipeline();

        // netty提供的处理http的编解码器
        pipeline.addLast("myHttpServerCodec", new HttpServerCodec());

        // 增加自定义handler
        pipeline.addLast("myHttpServerHandler", new HttpServerHandler());

    }

}
