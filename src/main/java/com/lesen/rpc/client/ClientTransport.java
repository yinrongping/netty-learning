package com.lesen.rpc.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.lesen.rpc.client.handler.ResponseHandlerFactory;
import com.lesen.rpc.client.handler.SyncResponseHandler;
import com.lesen.rpc.protobuf.LesenRPCProto.LesenRPCRequest;

public class ClientTransport {

	private static final long DEFAULT_REQUEST_TIMEOUT = 10000;

	private long requestTimeout;
	private String rpcUri;
	private volatile Channel channel;
	private ClientDispatcher handler;
	private ResponseHandlerFactory handlerFactory;

	public ClientTransport(String rpcUri, ClientDispatcher handler,
			ResponseHandlerFactory handlerFactory) {
		this(rpcUri, DEFAULT_REQUEST_TIMEOUT, handler, handlerFactory);
	}

	public ClientTransport(String rpcUri, long requestTimeout,
			ClientDispatcher handler, ResponseHandlerFactory handlerFactory) {
		this.rpcUri = rpcUri;
		this.handler = handler;
		this.requestTimeout = requestTimeout;
		this.handlerFactory = handlerFactory;
	}

	public Object call(LesenRPCRequest request) throws Exception {
		Thread requestThread = Thread.currentThread();
		SyncResponseHandler syncHandler = new SyncResponseHandler(requestThread);
		String remoteServiceName = request.getServiceName();
		handlerFactory.registHandler(remoteServiceName, syncHandler);
		channel.writeAndFlush(request);
		try {
			Thread.sleep(requestTimeout);
			RuntimeException exception = createTimeoutException(request);
			throw exception;
		} catch (InterruptedException e) {
			Thread.interrupted();
			checkHasException(syncHandler);
			return syncHandler.getReault();
		}
	}

	private void checkHasException(SyncResponseHandler syncHandler)
			throws Exception {
		Exception exception = syncHandler.getException();
		if (exception != null)
			throw exception;
	}

	public static long getDefaultRequestTimeout() {
		return DEFAULT_REQUEST_TIMEOUT;
	}

	public void close() {
		channel.close();
	}

	public void connectService() {
		BlockingQueue<String> initFlagQueue = new ArrayBlockingQueue<String>(1);
		BossRunnable bossRunnable = new BossRunnable(initFlagQueue);
		Thread bossThread = new Thread(bossRunnable);
		bossThread.start();
		waitChannelIsInit(initFlagQueue);
		System.out.println("init ok");
	}

	private RuntimeException createTimeoutException(LesenRPCRequest request) {
		String serviceName = request.getServiceName();
		String methodName = request.getMethodName();
		String format = "call service:%s method:%s timeout";
		String message = String.format(format, serviceName, methodName);
		RuntimeException exception = new RuntimeException(message);
		return exception;
	}

	private void waitChannelIsInit(BlockingQueue<String> queue) {
		try {
			queue.take();
		} catch (InterruptedException e) {
			throwExceptionIfInitfail();
		}

	}

	private void throwExceptionIfInitfail() {
		if (channel == null) {
			String message = String.format("connect %s fail", rpcUri);
			throw new RuntimeException(message);
		}
	}

	private final class BossRunnable implements Runnable {
		private final BlockingQueue<String> initFlagQueue;

		private BossRunnable(BlockingQueue<String> queue) {
			this.initFlagQueue = queue;
		}

		public void run() {
			EventLoopGroup workerGroup = new NioEventLoopGroup();
			ClientChannelIniter initer = new ClientChannelIniter(handler);
			SocketAddress serviceAddr = parseAddressFrom(rpcUri);
			try {
				Bootstrap b = new Bootstrap();
				b.group(workerGroup);
				b.channel(NioSocketChannel.class);
				b.option(ChannelOption.SO_KEEPALIVE, true);
				b.handler(initer);
				ChannelFuture f = b.connect(serviceAddr).sync();
				channel = f.channel();
				initFlagQueue.offer("start");
				channel.closeFuture().sync();
			} catch (Exception e) {
				initFlagQueue.offer("start");
				e.printStackTrace();
			} finally {
				workerGroup.shutdownGracefully();
			}
		}
	}

	/**
	 * 
	 * @param rpcUri
	 *            :rpc://host:port/[service]
	 * @return
	 */
	public SocketAddress parseAddressFrom(String rpcUri) {
		try {
			URI uri = new URI(rpcUri);
			String host = uri.getHost();
			int port = uri.getPort();
			InetSocketAddress address = new InetSocketAddress(host, port);
			return address;
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
}
