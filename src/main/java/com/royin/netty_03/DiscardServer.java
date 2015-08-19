package com.royin.netty_03;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * User: Roy.Yin
 * Date: 2015/6/4
 * Time: 11:44
 */
public class DiscardServer {

    private int port;

    public DiscardServer(int port){
        this.port = port;
    }

    public void run() throws Exception{

        EventLoopGroup bossGroup = new NioEventLoopGroup();

        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new TimeEncoder2(),new TimeServerHandler2());
                }
            }).option(ChannelOption.SO_BACKLOG,128).childOption(ChannelOption.SO_KEEPALIVE,true);

            //�󶨶˿ڣ���ʼ���ܽ���������
            ChannelFuture future = bootstrap.bind(port).sync();

            //�ȴ�������socket �ر�
            //����������У��ⲻ�ᷢ��������������ŵعر���ķ�����
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8080;
        }
        new DiscardServer(port).run();
    }
}
