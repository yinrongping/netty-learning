package com.royin.netty_03;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

/**
 * User: Roy.Yin
 * Date: 2015/6/4
 * Time: 13:19
 */
public class TimeClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf b = (ByteBuf)msg;

        try {
            long currentTime = (b.readUnsignedInt()-2208988800L)*1000L;
            System.out.println(new Date(currentTime));
            ctx.close();
        } finally {
            b.release();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
