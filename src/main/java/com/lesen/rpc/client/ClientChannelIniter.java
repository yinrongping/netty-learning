package com.lesen.rpc.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

import com.lesen.rpc.protobuf.LesenRPCProto.LesenRPCResponse;

class ClientChannelIniter extends ChannelInitializer<SocketChannel> {
	private ClientDispatcher handler;

	public ClientChannelIniter(ClientDispatcher handler) {
		this.handler = handler;
	}

	@Override
	public void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		LesenRPCResponse instance = LesenRPCResponse.getDefaultInstance();
		pipeline.addLast("Varint32Decoder", new ProtobufVarint32FrameDecoder());
		pipeline.addLast("protobufDecoder", new ProtobufDecoder(instance));
		pipeline.addLast("Varint32LDecoder",new ProtobufVarint32LengthFieldPrepender());
		pipeline.addLast("protobufEncoder", new ProtobufEncoder());
		pipeline.addLast("dispatcher", handler);
	}
}