package com.lesen.rpc.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.google.protobuf.ByteString;
import com.lesen.rpc.common.edecode.DecodeEnodeFactory;
import com.lesen.rpc.common.edecode.ParamEncodeDecode;
import com.lesen.rpc.protobuf.LesenRPCProto.LesenRPCParameter;
import com.lesen.rpc.protobuf.LesenRPCProto.LesenRPCRequest;

public class RemoteServiceProxy implements InvocationHandler {

	private String serverName;
	private ClientTransport transport;
	private DecodeEnodeFactory enodeFactory;

	public RemoteServiceProxy() {

	}

	public Object getProxy(Class<?> cls) {
		ClassLoader classLoader = cls.getClassLoader();
		Class<?>[] interfaces = null;
		if (cls.isInterface()) {
			interfaces = new Class[] { cls };
		} else {
			interfaces = cls.getInterfaces();
		}
		Object proxy = Proxy.newProxyInstance(classLoader, interfaces, this);
		return proxy;
	}


	public Object invoke(Object obj, Method method, Object[] arg)
			throws Throwable {
		LesenRPCRequest.Builder builder = LesenRPCRequest.newBuilder();
		LesenRPCParameter.Builder parBuilder = LesenRPCParameter.newBuilder();
		String token = genernateToken(method);
		builder.setToken(token);
		builder.setServiceName(serverName);
		builder.setMethodName(method.getName());

		if (arg != null) {
			ParamEncodeDecode edobj;
			for (Object object : arg) {
				edobj = enodeFactory.getEncodeDecode(object);
				parBuilder.setType(edobj.getTypeName());
				parBuilder.setValue(ByteString.copyFrom(edobj.encode(object)));
				builder.addParams(parBuilder.build());
			}
		}
		LesenRPCRequest request = builder.build();
		return transport.call(request);
	}

	
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public void setTransport(ClientTransport transport) {
		this.transport = transport;
	}

	public void setEnodeFactory(DecodeEnodeFactory enodeFactory) {
		this.enodeFactory = enodeFactory;
	}

	private String genernateToken(Method method) {
		final long currentTimeMillis = System.currentTimeMillis();
		return String.format("%s-%s", method.getName(), currentTimeMillis);
	}

}
