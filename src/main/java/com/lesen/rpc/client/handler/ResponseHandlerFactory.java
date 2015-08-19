package com.lesen.rpc.client.handler;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandlerFactory {

	private Map<String, ResponseHandler> handlers;

	public ResponseHandlerFactory() {
		handlers = new HashMap<String, ResponseHandler>();
	}

	public void registHandler(String serverName, ResponseHandler handler) {
		handlers.put(serverName, handler);
	}

	public void remove(String serverName) {
		handlers.remove(serverName);
	}

	public ResponseHandler getHandler(String serviceName) {
		return handlers.get(serviceName);
	}

	public void clear() {
		handlers.clear();
	}
}
