package com.lesen.rpc.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import com.lesen.rpc.client.handler.ResponseHandler;
import com.lesen.rpc.client.handler.ResponseHandlerFactory;
import com.lesen.rpc.protobuf.LesenRPCProto.LesenRPCResponse;

class ClientDispatcher extends ChannelInboundHandlerAdapter {

	private ResponseHandlerFactory handlerFactory;

	public ClientDispatcher(ResponseHandlerFactory handlerFactory) {
		this.handlerFactory = handlerFactory;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		LesenRPCResponse response = (LesenRPCResponse) msg;
		String serviceName = response.getServiceName();
		ResponseHandler handler = handlerFactory.getHandler(serviceName);
		handler.onResponse(response);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
//		System.out.println(cause.getLocalizedMessage());
		cause.printStackTrace();
		ctx.close();
	}

}