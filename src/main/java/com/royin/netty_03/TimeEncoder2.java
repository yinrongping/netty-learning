package com.royin.netty_03;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * User: Roy.Yin
 * Date: 2015/6/4
 * Time: 16:29
 */
public class TimeEncoder2 extends MessageToByteEncoder<UnixTime>{


    @Override
    protected void encode(ChannelHandlerContext ctx, UnixTime msg, ByteBuf out) throws Exception {
        out.writeInt((int)msg.getValue());
    }
}
