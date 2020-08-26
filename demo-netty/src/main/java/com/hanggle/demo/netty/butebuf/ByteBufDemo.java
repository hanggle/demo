package com.hanggle.demo.netty.butebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

public class ByteBufDemo {
    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello world", CharsetUtil.UTF_8);

        byte[] array = byteBuf.array();
        System.out.println(new String(array, CharsetUtil.UTF_8));

        System.out.println(byteBuf.readerIndex());
        System.out.println(byteBuf.writerIndex());
        System.out.println(byteBuf.capacity());
        System.out.println(byteBuf.retain());

    }
}
