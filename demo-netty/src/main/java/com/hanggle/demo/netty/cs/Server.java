package com.hanggle.demo.netty.cs;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * netty server端
 */
public class Server {
    public static void main(String[] args) throws InterruptedException {

        // bossGroup 用户处理连接，workGroup用于处理连接后的业务，两个均是无线循环
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128) //设置线程队列得到的连接数
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .handler(null) // bossGroup中的handler
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ServerHandler());
                        }
                    }); // workGroup中的handler
            System.out.println("服务器已准备好");

            // 绑定端口,并且同步。同时启动服务器
            ChannelFuture cf = serverBootstrap.bind(8088).sync();

            // 监听关闭通道
            cf.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

}
