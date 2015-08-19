package com.lesen.rpc.client.handler;

import com.lesen.rpc.protobuf.LesenRPCProto.LesenRPCResponse;

public interface ResponseHandler {

	void onResponse(LesenRPCResponse response);

	Object getReault();

	Exception getException();

}
