package com.royin.netty_03;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * User: Roy.Yin
 * Date: 2015/6/4
 * Time: 16:29
 */
public class TimeEncoder extends ChannelOutboundHandlerAdapter{

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {

        UnixTime time = (UnixTime)msg;

        ByteBuf buf = ctx.alloc().buffer(4);

        buf.writeInt((int)time.getValue());

        ctx.write(buf,promise);
    }
}
