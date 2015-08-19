package com.royin.netty_03;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * User: Roy.Yin
 * Date: 2015/6/4
 * Time: 13:08
 */
public class TimeServerHandler2 extends ChannelInboundHandlerAdapter{

    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        ChannelFuture f = ctx.writeAndFlush(new UnixTime());

        f.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
