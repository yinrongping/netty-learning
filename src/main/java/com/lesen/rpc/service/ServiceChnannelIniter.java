package com.lesen.rpc.service;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

import com.lesen.rpc.protobuf.LesenRPCProto.LesenRPCRequest;


final class ServiceChnannelIniter extends ChannelInitializer<SocketChannel> {

	private ServiceDispatcher handler;

	public ServiceChnannelIniter(ServiceDispatcher handler) {
		this.handler = handler;
	}

	@Override
	public void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		LesenRPCRequest request = LesenRPCRequest.getDefaultInstance();
		pipeline.addLast("Varint32Decoder", new ProtobufVarint32FrameDecoder());
		pipeline.addLast("protobufDecoder", new ProtobufDecoder(request));
		pipeline.addLast("Varint32LDecoder",new ProtobufVarint32LengthFieldPrepender());
		pipeline.addLast("protobufEncoder", new ProtobufEncoder());
		pipeline.addLast("dispatcher", handler);
	}

}