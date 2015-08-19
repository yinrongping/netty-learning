package com.lesen.rpc.interceptor;

import com.lesen.rpc.protobuf.LesenRPCProto.LesenRPCRequest;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

public interface Interceptor {

	void onServiceStart(Channel channel);

	void onServiceStop(Channel channel);

	void beforeCall(ChannelHandlerContext context, LesenRPCRequest msg);

	void afterCall(ChannelHandlerContext context, LesenRPCRequest msg);
}
