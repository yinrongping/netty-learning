package com.lesen.rpc.interceptor;

import java.net.SocketAddress;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import com.lesen.rpc.protobuf.LesenRPCProto.LesenRPCRequest;

public class DefaultInterceptor implements Interceptor {


	public void onServiceStart(Channel channel) {
		System.err.println("service is running");
	}


	public void onServiceStop(Channel channel) {
		System.err.println("service is close");
	}


	public void beforeCall(ChannelHandlerContext context, LesenRPCRequest msg) {
		Channel channel = context.channel();
		SocketAddress remoteAddress = channel.remoteAddress();
		String client = remoteAddress.toString();
		String serviceName = msg.getServiceName();
		String methdoName = msg.getMethodName();
		String message = client + "-call-" + serviceName + "." + methdoName+ " start";
		System.err.println(message);
	}


	public void afterCall(ChannelHandlerContext context, LesenRPCRequest msg) {
		Channel channel = context.channel();
		SocketAddress remoteAddress = channel.remoteAddress();
		String client = remoteAddress.toString();
		String serviceName = msg.getServiceName();
		String methdoName = msg.getMethodName();
		String message = client + "-call-" + serviceName + "." + methdoName+ " finished";
		System.err.println(message);
	}

}
