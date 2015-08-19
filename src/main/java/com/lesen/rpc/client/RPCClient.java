package com.lesen.rpc.client;

import com.lesen.rpc.client.handler.ResponseHandlerFactory;
import com.lesen.rpc.common.edecode.DecodeEnodeFactory;
import com.lesen.rpc.common.edecode.ParamEncodeDecode;

public class RPCClient {

	private volatile boolean initFlag;
	private ClientDispatcher handler;
	private ClientTransport transport;
	private DecodeEnodeFactory enodeFactory;
	private ResponseHandlerFactory handlerFactory;

	public RPCClient(String rpcUri) {
		this.handlerFactory = new ResponseHandlerFactory();
		this.handler = new ClientDispatcher(handlerFactory);
		this.enodeFactory = DecodeEnodeFactory.getInstance();
		this.transport = new ClientTransport(rpcUri, handler, handlerFactory);
	}

	public <T> T getRemoteService(String serviceName, Class<T> serviceCls) {
		this.connectService();
		RemoteServiceProxy wraper = new RemoteServiceProxy();
		wraper.setTransport(transport);
		wraper.setServerName(serviceName);
		wraper.setEnodeFactory(enodeFactory);
		@SuppressWarnings("unchecked")
		T proxy = (T) wraper.getProxy(serviceCls);
		return proxy;
	}

	public void connectService() {
		if (initFlag)
			return;
		transport.connectService();
		initFlag = true;
	}

	public void registDecodeEncode(ParamEncodeDecode dcodeEncode) {
		enodeFactory.registDecodeEncode(dcodeEncode);
	}

	public void close() {
		handlerFactory.clear();
		transport.close();
	}
}
