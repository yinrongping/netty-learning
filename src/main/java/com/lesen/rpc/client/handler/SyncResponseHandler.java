package com.lesen.rpc.client.handler;

import com.lesen.rpc.common.edecode.DecodeEnodeFactory;
import com.lesen.rpc.common.edecode.ParamEncodeDecode;
import com.lesen.rpc.protobuf.LesenRPCProto.LesenRPCResponse;
import com.lesen.rpc.protobuf.LesenRPCProto.LesenRPCResult;

public class SyncResponseHandler implements ResponseHandler {

	private final Thread requestThread;
	private volatile Object result;
	private volatile Exception exception;

	public SyncResponseHandler(Thread requestThread) {
		this.requestThread = requestThread;
	}


	public Object getReault() {
		return result;
	}


	public Exception getException() {
		return exception;
	}


	public void onResponse(LesenRPCResponse response) {
		if (response.hasError()) {
			exception = createException(response);
		} else {
			LesenRPCResult rpcResult = response.getReault(0);
			String clsName = rpcResult.getType();
			DecodeEnodeFactory manager = DecodeEnodeFactory.getInstance();
			ParamEncodeDecode decode = manager.getEncodeDecode(clsName);
			result = decode.decode(rpcResult.getValue().toByteArray());
		}
		notifyRequestComplated();
	}

	private void notifyRequestComplated() {
		requestThread.interrupt();
	}

	private Exception createException(LesenRPCResponse response) {
		String errorMessage = response.getError();
		RuntimeException exception = new RuntimeException(errorMessage);
		return exception;
	}

}