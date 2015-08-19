package com.lesen.rpc.service;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.Method;
import java.util.List;

import com.google.protobuf.ByteString;
import com.lesen.rpc.common.edecode.DecodeEnodeFactory;
import com.lesen.rpc.common.edecode.ParamEncodeDecode;
import com.lesen.rpc.interceptor.Interceptor;
import com.lesen.rpc.interceptor.InterceptorFactory;
import com.lesen.rpc.protobuf.LesenRPCProto.Error;
import com.lesen.rpc.protobuf.LesenRPCProto.LesenRPCParameter;
import com.lesen.rpc.protobuf.LesenRPCProto.LesenRPCRequest;
import com.lesen.rpc.protobuf.LesenRPCProto.LesenRPCResponse;
import com.lesen.rpc.protobuf.LesenRPCProto.LesenRPCResult;

@Sharable
public class ServiceDispatcher extends
		SimpleChannelInboundHandler<LesenRPCRequest> {

	private ServiceExportManager servicesManager;
	private InterceptorFactory interceptorFactory;
	private DecodeEnodeFactory decodeEnodeFactory;

	public ServiceDispatcher(ServiceExportManager servicesManager,
			InterceptorFactory interceptorFactory,
			DecodeEnodeFactory decodeEnodeFactory) {
		this.servicesManager = servicesManager;
		this.interceptorFactory = interceptorFactory;
		this.decodeEnodeFactory = decodeEnodeFactory;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, LesenRPCRequest msg)
			throws Exception {
		notifyAllInteceptorBefore(ctx, msg);
		if (!checkServiceIsExist(ctx, msg))
			return;
		if (!checkMethodIsExist(ctx, msg))
			return;
		if (!checkParamIsMatch(ctx, msg))
			return;
		processRequest(ctx, msg);
		notifyAllInteceptorAfter(ctx, msg);

	}

	private void notifyAllInteceptorAfter(ChannelHandlerContext ctx,
			LesenRPCRequest msg) {
		List<Interceptor> interceptors = interceptorFactory.getInterceptors();
		for (Interceptor interceptor : interceptors) {
			interceptor.afterCall(ctx, msg);
		}
	}

	private void notifyAllInteceptorBefore(ChannelHandlerContext ctx,
			LesenRPCRequest msg) {
		List<Interceptor> interceptors = interceptorFactory.getInterceptors();
		for (Interceptor interceptor : interceptors) {
			interceptor.beforeCall(ctx, msg);
		}
	}

	private void processRequest(ChannelHandlerContext ctx, LesenRPCRequest msg) {
		try {
			Object obj = invokeLocalService(msg);
			LesenRPCResponse response = encodResult(obj, msg);
			response.getSerializedSize();
			ctx.writeAndFlush(response);
		} catch (Exception e) {
			processInvokeError(ctx, msg, e);
		}
	}

	private void processInvokeError(ChannelHandlerContext ctx,
			LesenRPCRequest msg, Exception e) {
		String format = "service excption :%s";
		Throwable cause = e.getCause();
		StackTraceElement[] elements = cause.getStackTrace();
		String trace = elements[0].toString();
		String errorMessage = String.format(format, trace);
		sendErrorResponse(ctx, msg, errorMessage);
	}

	private LesenRPCResponse encodResult(Object obj, LesenRPCRequest msg) {
		ParamEncodeDecode encode = decodeEnodeFactory.getEncodeDecode(obj);
		LesenRPCResponse.Builder builder = LesenRPCResponse.newBuilder();
		LesenRPCResult.Builder resBuilder = LesenRPCResult.newBuilder();
		builder.setServiceName(msg.getServiceName());
		builder.setToken(msg.getToken());
		builder.setHasError(false);
		
		byte[] objBytes = encode.encode(obj);
		resBuilder.setType(encode.getTypeName());
		resBuilder.setValue(ByteString.copyFrom(objBytes));
		LesenRPCResult reault = resBuilder.build();
		builder.addReault(reault);
		LesenRPCResponse response = builder.build();
		return response;
	}

	private Object invokeLocalService(LesenRPCRequest msg) throws Exception {
		Method mth = getServiceMatchMethod(msg);
		Object service = servicesManager.getService(msg.getServiceName());
		Object[] args = parserParmater(msg);
		Object obj = mth.invoke(service, args);
		return obj;
	}

	private Object[] parserParmater(LesenRPCRequest msg) {
		List<LesenRPCParameter> parms = msg.getParamsList();
		int size = parms.size();
		ParamEncodeDecode encodeDecode;
		Object[] objs = new Object[size];
		DecodeEnodeFactory manager = DecodeEnodeFactory.getInstance();
		for (int i = 0; i < size; i++) {
			LesenRPCParameter pa = parms.get(i);
			String paramType = pa.getType();
			ByteString value = pa.getValue();
			byte[] byteArray = value.toByteArray();
			encodeDecode = manager.getEncodeDecode(paramType);
			objs[i] = encodeDecode.decode(byteArray);
		}
		return objs;
	}

	private boolean checkParamIsMatch(ChannelHandlerContext ctx,
			LesenRPCRequest msg) {
		Method mth = getServiceMatchMethod(msg);
		Class<?>[] cls = mth.getParameterTypes();
		if (cls.length != msg.getParamsCount()) {
			String methodNotMatch = Error.METHOD_NOT_MATCH.toString();
			sendErrorResponse(ctx, msg, methodNotMatch);
			return false;
		}
		List<LesenRPCParameter> parms = msg.getParamsList();
		String methodNotMatch = Error.METHOD_NOT_MATCH.toString();
		for (int i = 0; i < cls.length; i++) {
			String serviceType = cls[i].getName();
			String clientType = parms.get(i).getType();
			ParamEncodeDecode pedService = decodeEnodeFactory.getEncodeDecode(serviceType);
			ParamEncodeDecode pedClient = decodeEnodeFactory.getEncodeDecode(clientType);
			
			if (!pedService.equals(pedClient)) {
				sendErrorResponse(ctx, msg, methodNotMatch);
				return false;
			}
		}
		return true;
	}

	private void sendErrorResponse(ChannelHandlerContext ctx,
			LesenRPCRequest msg, String erro) {
		LesenRPCResponse response = buildErrorResponse(msg, erro);
		ctx.writeAndFlush(response);
	}

	private boolean checkMethodIsExist(ChannelHandlerContext ctx,
			LesenRPCRequest msg) {
		Method mth = getServiceMatchMethod(msg);
		if (mth != null) {
			return true;
		}
		String methodNotExist = Error.METHOD_NOT_EXIST.toString();
		sendErrorResponse(ctx, msg, methodNotExist);
		return false;
	}

	private Method getServiceMatchMethod(LesenRPCRequest msg) {
		String methodName = msg.getMethodName();
		String serviceName = msg.getServiceName();
		Object service = servicesManager.getService(serviceName);
		Method[] mths = service.getClass().getDeclaredMethods();
		for (Method method : mths) {
			if (method.getName().equals(methodName))
				return method;
		}
		return null;
	}

	private boolean checkServiceIsExist(ChannelHandlerContext ctx,
			LesenRPCRequest msg) {
		Object service = servicesManager.getService(msg.getServiceName());
		if (service != null) {
			return true;
		}
		String serviceNotExist = Error.SERVICE_NOT_EXIST.toString();
		sendErrorResponse(ctx, msg, serviceNotExist);
		return false;
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		System.out.println(cause);
	}

	private LesenRPCResponse buildErrorResponse(LesenRPCRequest msg,
			String error) {
		LesenRPCResponse.Builder builder = LesenRPCResponse.newBuilder();
		builder.setServiceName(msg.getServiceName());
		builder.setToken(msg.getToken());
		builder.setHasError(true);
		builder.setError(error);
		LesenRPCResponse response = builder.build();
		return response;
	}

}
