package com.waylau.netty;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * ��Channel�����ͻ���
 * 
 * @author c.k
 * 
 */
public class BootstrapingFromChannel {
	
	public static void main(String[] args) throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		ServerBootstrap b = new ServerBootstrap();
		b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
				.childHandler(new SimpleChannelInboundHandler<ByteBuf>() {
					ChannelFuture connectFuture;

					@Override
					public void channelActive(ChannelHandlerContext ctx) throws Exception {
						Bootstrap b = new Bootstrap();
						b.channel(NioSocketChannel.class).handler(
								new SimpleChannelInboundHandler<ByteBuf>() {
									@Override
									protected void channelRead0(ChannelHandlerContext ctx,
											ByteBuf msg) throws Exception {
										System.out.println("Received data");
										msg.clear();
									}
								});
						b.group(ctx.channel().eventLoop());
						connectFuture = b.connect(new InetSocketAddress("127.0.0.1", 2048));
					}

					@Override
					protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg)
							throws Exception {
						if (connectFuture.isDone()) {
							// do something with the data
						}
					}
				});
		ChannelFuture f = b.bind(2048);
		f.addListener(new ChannelFutureListener() {

			public void operationComplete(ChannelFuture future) throws Exception {
				if (future.isSuccess()) {
					System.out.println("Server bound");
				} else {
					System.err.println("bound fail");
					future.cause().printStackTrace();
				}
			}
		});
	}
}
