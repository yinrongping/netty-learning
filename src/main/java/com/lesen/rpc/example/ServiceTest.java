package com.lesen.rpc.example;

import com.lesen.rpc.common.export.TestService;
import com.lesen.rpc.service.RPCService;

public class ServiceTest {

	public static void main(String[] args) throws Exception {
		RPCService app = new RPCService(1082);
		app.registDecodeEncode(new PersonDecodeEncode());
		app.exportService("test", new TestService());
		app.run();
	}
}
