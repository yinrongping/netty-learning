package com.royin.netty_03;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * User: Roy.Yin
 * Date: 2015/6/4
 * Time: 13:24
 */
public class TimeClient {

    private void run(String host, int port) throws Exception{

        EventLoopGroup workerGroup = new NioEventLoopGroup();

        Bootstrap b = new Bootstrap();

        try {
            b.group(workerGroup)
             .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new TimeDecoder3(),new TimeClientHandler3());
                        }
                    });

            //启动客户端
            ChannelFuture f = b.connect(host,port).sync();

            //等待连接关闭
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception{
        new TimeClient().run("127.0.0.1",8080);
    }
}
