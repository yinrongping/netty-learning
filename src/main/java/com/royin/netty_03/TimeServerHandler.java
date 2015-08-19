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
public class TimeServerHandler extends ChannelInboundHandlerAdapter{

    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        //分配一个4个字节的缓存空间
        final ByteBuf time = ctx.alloc().buffer(4);

        time.writeInt((int)(System.currentTimeMillis()/1000L+2208988800L));

        final ChannelFuture f =  ctx.writeAndFlush(time);

        f.addListener(new ChannelFutureListener() {

            public void operationComplete(ChannelFuture future) throws Exception {
                assert f == future;
                ctx.close();
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
