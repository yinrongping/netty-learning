package com.royin.netty_03;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * User: Roy.Yin
 * Date: 2015/6/4
 * Time: 11:42
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter{

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //super.channelRead(ctx, msg);

        //1. 默默地丢弃收到的数据
        //((ByteBuf)msg).release();

        //2. 将传输的数据打印
        /*ByteBuf byteBuf = (ByteBuf)msg;
        try {
            while(byteBuf.isReadable()){
                System.out.println((char)byteBuf.readByte());
                System.out.flush();
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }*/

//        ctx.write(msg);
//        ctx.flush();

        ctx.writeAndFlush(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //super.exceptionCaught(ctx, cause);
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}
