package com.lesen.rpc.service;

import java.util.List;

import com.lesen.rpc.interceptor.Interceptor;
import com.lesen.rpc.interceptor.InterceptorFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class ServiceTransport {

	private int port;
	private Channel channel;
	private ServiceDispatcher handler;
	private InterceptorFactory interceptorFactory;

	public ServiceTransport(int port, ServiceDispatcher handler,
			InterceptorFactory interceptorFactory) {
		this.port = port;
		this.handler = handler;
		this.interceptorFactory = interceptorFactory;
	}

	public void run() throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		ServiceChnannelIniter initer = new ServiceChnannelIniter(handler);
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup);
			b.channel(NioServerSocketChannel.class);
			b.childHandler(initer);
			b.option(ChannelOption.SO_BACKLOG, 128);
			b.option(ChannelOption.TCP_NODELAY, true);
			b.childOption(ChannelOption.SO_KEEPALIVE, true);
			ChannelFuture bind = b.bind(port);
			ChannelFuture f = bind.sync();
			channel = f.channel();
			noticeAllInteceptorOnStart(channel);
			channel.closeFuture().sync();
			noticeAllInteceptorOnClose(channel);
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}

	private void noticeAllInteceptorOnClose(Channel channel) {
		List<Interceptor> interceptors = interceptorFactory.getInterceptors();
		for (Interceptor interceptor : interceptors) {
			interceptor.onServiceStart(channel);
		}
	}

	private void noticeAllInteceptorOnStart(Channel channel) {
		List<Interceptor> interceptors = interceptorFactory.getInterceptors();
		for (Interceptor interceptor : interceptors) {
			interceptor.onServiceStart(channel);
		}
	}

	public void stop() {
		if (channel != null)
			channel.close();
	}

}
